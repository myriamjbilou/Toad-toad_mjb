package com.toad.controllers;

// Importation des classes nécessaires pour gérer les dates, listes et maps
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.Rental;
import com.toad.repositories.RentalRepository;

// Cette classe est un contrôleur qui va gérer les routes liées aux locations (rental)
@Controller
@RequestMapping(path = "/toad/rental")  // Préfixe d'URL commun à toutes les méthodes de ce contrôleur
public class RentalController {

    // Injection du repository pour accéder à la base de données des locations
    @Autowired
    private RentalRepository rentalRepository;

    // Méthode qui permet de mettre à jour une location existante
    @PutMapping(path = "/update/{rental_id}")
    public @ResponseBody String updateRental(
            @PathVariable Integer rental_id,
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {

        // On recherche la location à partir de son ID
        Rental rental = rentalRepository.findById(rental_id).orElse(null);
        String message_retour;

        if (rental != null) {
            // Si on la trouve, on met à jour ses champs
            rental.setRentalId(rental_id);
            rental.setRentalDate(rental_date);
            rental.setInventoryId(inventory_id);
            rental.setCustomerId(customer_id);
            rental.setReturnDate(return_date);
            rental.setStaffId(staff_id);
            rental.setLastUpdate(last_update);
            rentalRepository.save(rental); // Sauvegarde dans la base

            message_retour = "Rental Updated";
        } else {
            message_retour = "Rental not found"; // Si non trouvée
        }
        return message_retour;
    }

    // Méthode pour créer une nouvelle location (POST)
    @PostMapping(path = "/add")
    public @ResponseBody String createRental(
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {

        // Création d'une nouvelle instance de Rental
        Rental newRental = new Rental();
        newRental.setRentalId(0); // Laisser la base générer l'ID
        newRental.setRentalDate(rental_date);
        newRental.setInventoryId(inventory_id);
        newRental.setCustomerId(customer_id);
        newRental.setReturnDate(return_date);
        newRental.setStaffId(staff_id);
        newRental.setLastUpdate(last_update);

        rentalRepository.save(newRental); // On sauvegarde en BDD

        return "Location créée avec succès !";
    }

    // Endpoint spécifique pour créer une location (sans return date)
    @PostMapping(path = "/rent")
    public ResponseEntity<String> rentDVD(
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id) {
        try {

            // On vérifie si ce DVD est déjà loué (non retourné)
            List<Rental> rentals = rentalRepository.findByInventoryIdAndReturnDateIsNull(inventory_id);
            if (!rentals.isEmpty()) {
                return ResponseEntity.status(409).body("DVD déjà loué.");
            }

            // Récupère la date et l'heure actuelles au format ISO
            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            // On crée une nouvelle location
            Rental rental = new Rental();
            rental.setRentalDate(now);
            rental.setInventoryId(inventory_id);
            rental.setCustomerId(customer_id);
            rental.setReturnDate(null); // Le DVD n'est pas encore retourné (location en cours)
            rental.setStaffId(1); // ID Valeur par défaut 
            rental.setLastUpdate(now);

            rentalRepository.save(rental); // Sauvegarde en base

            return ResponseEntity.ok("DVD loué avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // Log console
            return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
        }
    }

    // Suppression d'une location via son ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteRental(@PathVariable Integer id) {
        String message;
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
            message = "Location supprimée";
        } else {
            message = "Location avec cet ID n'existe pas";
        }
        return message;
    }

    // Récupère toutes les locations
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Rental> getAllUsers() {
        return rentalRepository.findAll();
    }

    // Récupère une location précise avec son ID
    @GetMapping(path = "/getById")
    public @ResponseBody Rental getRentalById(@RequestParam Integer id) {
        Rental rental = rentalRepository.findById(id).orElse(null);

        // verifie si la location existe (pas null)
        if (rental != null) { 
            Rental filteredRental = new Rental();
            filteredRental.setRentalId(rental.getRentalId());
            filteredRental.setRentalDate(rental.getRentalDate());
            filteredRental.setInventoryId(rental.getInventoryId());
            filteredRental.setCustomerId(rental.getCustomerId());
            filteredRental.setReturnDate(rental.getReturnDate());
            filteredRental.setStaffId(rental.getStaffId());
            filteredRental.setLastUpdate(rental.getLastUpdate());
            return filteredRental;
        }
        return null; // Si aucune location trouvée, retourne null
    }
}
