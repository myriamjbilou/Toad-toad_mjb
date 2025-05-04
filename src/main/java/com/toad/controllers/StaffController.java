package com.toad.controllers;

// Importation des classes nécessaires à Spring Boot et au traitement HTTP
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toad.entities.Staff;
import com.toad.repositories.StaffRepository;

// Déclare que cette classe est un contrôleur REST (retourne des données, pas des vues HTML)
@RestController

// Définit le chemin de base de toutes les routes de cette classe
@RequestMapping("/toad/staff") 
public class StaffController {

    // Injection automatique du repository Staff pour accéder à la base de données
    @Autowired
    private StaffRepository staffRepository;

    // Route GET pour récupérer un membre du personnel à partir de son email
    @GetMapping("/getByEmail")
    public ResponseEntity<?> getStaffByEmail(@RequestParam String email) {
        // Recherche d’un staff avec l’email passé en paramètre
        Staff staff = staffRepository.findByEmail(email);
        // Si un staff est trouvé, on retourne le staff avec le code HTTP 200 OK
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            // Sinon, on retourne un message d’erreur avec le code HTTP 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun membre du staff trouvé avec l'email : " + email);
        }
    }
}
