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

import com.toad.entities.Director;
import com.toad.repositories.DirectorRepository;

@Controller
@RequestMapping(path = "/toad/director")
public class DirectorController {

  // Injection du repository DirectorRepository pour interagir avec la base de
  // données
  @Autowired
  private DirectorRepository directorRepository;

  // Méthode appelée lors d'une requête POST vers /toad/director/create
  // Elle permet d'ajouter un nouveau réalisateur à la base
  @PostMapping(path = "/create")
  public @ResponseBody String createDirector(
      @RequestParam Integer director_id,
      @RequestParam String nom,
      @RequestParam String prenom,
      @RequestParam String date_naissance,
      @RequestParam String nationnalite) {

    // Création d'un nouvel objet Director et assignation des données
    Director newDirector = new Director();
    newDirector.setdirector_id(director_id);
    newDirector.setnom(nom);
    newDirector.setprenom(prenom);
    newDirector.setdate_naissance(date_naissance);
    newDirector.setnationnalite(nationnalite);

    // Sauvegarde du réalisateur dans la base via le repository
    directorRepository.save(newDirector);

    // Message de confirmation
    return "Réalisateur enregistré avec succès !";
  }

  // Requête GET vers /toad/director/getById?id=1
  // Permet de récupérer un réalisateur à partir de son ID
  @GetMapping(path = "/getById")
  public @ResponseBody Director getFilmById(@RequestParam Integer id) {
    return directorRepository.findById(id).orElse(null);
  }

  // Requête GET vers /toad/director/all
  // Renvoie tous les réalisateurs présents dans la base sous forme de liste JSON
  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Director> getAllDirectors() {

    // .findAll() est une méthode de Spring Data qui retourne tous les
    // enregistrements
    return directorRepository.findAll();
  }

  // Requête PUT vers /toad/director/update/{id}
  // Permet de modifier un réalisateur existant
  @PutMapping(path = "/update/{id}")
  public @ResponseBody String updateFilm(
      @PathVariable Integer id,
      @RequestParam String nom,
      @RequestParam String prenom,
      @RequestParam java.sql.Timestamp date_naissance,
      @RequestParam String nationalite) {

    String status = null;

    // Recherche du réalisateur en base
    Director director = directorRepository.findById(id).orElse(null);
    if (director == null) {
      // Si aucun réalisateur avec cet ID, retour d'erreur
      status = "Réalisateur non trouvé";
    } else {
      Director n = new Director();
      // Sinon, mise à jour des champs
      n.setnom(nom);
      n.setprenom(prenom);
      n.setdate_naissance(nationalite);
      n.setnationnalite(nationalite);
      // Sauvegarde des nouvelles données
      directorRepository.save(n);
      status = "Réalisateur Mis à jour";
    }
    return status;

  }

  // Route : DELETE /toad/director/delete/{id}
  // Supprime le réalisateur correspondant à l’ID indiqué dans l’URL
  @DeleteMapping(path = "/delete/{id}")
  public @ResponseBody String deleteFilm(@PathVariable Integer id) {
    String status = null;

    // On vérifie d'abord que le réalisateur existe
    if (directorRepository.existsById(id)) {
      // Si oui, on supprime et on retourne un message de succès
      directorRepository.deleteById(id);
      status = "Réalisateur supprimé";
    } else {
      // Sinon, on indique qu'il n'a pas été trouvé
      status = "Réalisateur pas trouvé";
    }
    return status;
  }
}