package com.toad.controllers;

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

@Controller
@RequestMapping(path = "/toad/rental")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @PutMapping(path = "/update/{rental_id}")
    public @ResponseBody String updateRental(
            @PathVariable Integer rental_id,
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {

        Rental rental = rentalRepository.findById(rental_id).orElse(null);
        String message_retour;

        if (rental != null) {
            rental.setRentalId(rental_id);
            rental.setRentalDate(rental_date);
            rental.setInventoryId(inventory_id);
            rental.setCustomerId(customer_id);
            rental.setReturnDate(return_date);
            rental.setStaffId(staff_id);
            rental.setLastUpdate(last_update);
            rentalRepository.save(rental);

            message_retour = "Rental Updated";
        } else {
            message_retour = "Rental not found";
        }
        return message_retour;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String createRental(
            @RequestParam String rental_date,
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id,
            @RequestParam String return_date,
            @RequestParam Integer staff_id,
            @RequestParam String last_update) {

        Rental newRental = new Rental();
        newRental.setRentalId(0);
        newRental.setRentalDate(rental_date);
        newRental.setInventoryId(inventory_id);
        newRental.setCustomerId(customer_id);
        newRental.setReturnDate(return_date);
        newRental.setStaffId(staff_id);
        newRental.setLastUpdate(last_update);

        rentalRepository.save(newRental);

        return "Location créée avec succès !";
    }

    // Nouveau endpoint /rent qui crée une location sans vérification préalable
    @PostMapping(path = "/rent")
    public ResponseEntity<String> rentDVD(
            @RequestParam Integer inventory_id,
            @RequestParam Integer customer_id) {
        try {

            // Vérification : ce DVD est-il déjà loué ?
            List<Rental> rentals = rentalRepository.findByInventoryIdAndReturnDateIsNull(inventory_id);
            if (!rentals.isEmpty()) {
                return ResponseEntity.status(409).body("DVD déjà loué.");
            }

            // Récupère la date et l'heure actuelles au format ISO
            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            Rental rental = new Rental();
            rental.setRentalDate(now);
            rental.setInventoryId(inventory_id);
            rental.setCustomerId(customer_id);
            rental.setReturnDate(null); // Le DVD n'est pas encore retourné
            rental.setStaffId(1); // Valeur par défaut (à adapter si nécessaire)
            rental.setLastUpdate(now);

            rentalRepository.save(rental);

            return ResponseEntity.ok("DVD loué avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // Log console
            return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
        }
    }

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

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Rental> getAllUsers() {
        return rentalRepository.findAll();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Rental getRentalById(@RequestParam Integer id) {
        Rental rental = rentalRepository.findById(id).orElse(null);
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
        return null;
    }
}
