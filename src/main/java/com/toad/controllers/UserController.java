/* 
package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.User;
import com.toad.repositories.UserRepository;

@Controller
@RequestMapping(path = "/toad/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Sauvegardé";
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteFilm(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "Location Supprimée";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody User getUserById(@RequestParam Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            User filteredUser = new User();
            filteredUser.setId(user.getId());
            filteredUser.setName(user.getName());
            filteredUser.setEmail(user.getEmail());
            return filteredUser;
        }
        return null;
    }

    // Nouveau endpoint : /toad/user/getByEmail?email=...
    @GetMapping(path = "/getByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun utilisateur trouvé avec l'e-mail : " + email);
        }
    }
}*/