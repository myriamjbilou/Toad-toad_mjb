package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toad.entities.Staff;
import com.toad.repositories.StaffRepository;

@RestController
@RequestMapping("/toad/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/getByEmail")
    public ResponseEntity<?> getStaffByEmail(@RequestParam String email) {
        Staff staff = staffRepository.findByEmail(email);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun membre du staff trouvé avec l'email : " + email);
        }
    }
}
