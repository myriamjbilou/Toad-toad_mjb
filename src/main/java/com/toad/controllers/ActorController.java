package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.Actor;
import com.toad.repositories.ActorRepository;

@Controller
@RequestMapping(path = "/toad/actor")
public class ActorController {

    // Injection automatique du repository qui donne accès à la base des acteurs
    @Autowired
    private ActorRepository ActorRepository;

    // Ajoute un acteur (requête POST vers /toad/actor/add)
    @PostMapping(path = "/add")
    public @ResponseBody String createActor(
            @RequestParam String first_name,
            @RequestParam String last_name) {

        Actor newActor = new Actor();
        newActor.setFirst_name(first_name);
        newActor.setLast_name(last_name);

        ActorRepository.save(newActor);

        return "Acteur enregistré avec succès !";
    }

    // Liste tous les acteurs
    // Renvoie tous les acteurs enregistrés (GET /toad/actor/all)
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Actor> getAllActors() {
        return ActorRepository.findAll();
    }

    // Récupère un acteur précis en fonction de son ID
    @GetMapping(path = "/getById")
    public @ResponseBody Actor getActorById(@RequestParam Integer id) {
        return ActorRepository.findById(id).orElse(null);
    }

    // Modifie un acteur existant (requête PUT /toad/actor/update/{actor_id})
    @PutMapping(path = "/update/{actor_id}")
    public @ResponseBody String updateActor(
            @PathVariable Integer id,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String last_update) {

        // Recherche de l'acteur à modifier        
        Actor actor = ActorRepository.findById(id).orElse(null);
        if (actor == null) {
            return "Acteur non trouvé";
        }

        // Mise à jour des champs
        actor.setFirst_name(first_name);
        actor.setLast_name(last_name);
        actor.setLast_update(last_update);

        // Sauvegarde dans la base
        ActorRepository.save(actor);
        return "Acteur Mis à jour";
    }

    // Supprime un acteur à partir de son ID (DELETE /toad/actor/delete/{id})
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteFilm(@PathVariable Integer id) {
        ActorRepository.deleteById(id);
        return "Acteur Supprimé";
    }
}
