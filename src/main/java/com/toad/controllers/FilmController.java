package com.toad.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.toad.entities.Film;
import com.toad.repositories.FilmRepository;

@Controller
@RequestMapping("/toad/film")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @PostMapping("/add")
    public @ResponseBody String addNewFilm(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer releaseYear,
            @RequestParam Integer languageId,
            @RequestParam(required = false) Integer originalLanguageId,
            @RequestParam Integer rentalDuration,
            @RequestParam Double rentalRate,
            @RequestParam Integer length,
            @RequestParam Double replacementCost,
            @RequestParam String rating,
            @RequestParam String lastUpdate // format attendu "yyyy-MM-dd HH:mm:ss"
    ) {
        System.out.println("=== ADD NEW FILM ===");
        System.out.println("Paramètres reçus:");
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        System.out.println("releaseYear: " + releaseYear);
        System.out.println("languageId: " + languageId);
        System.out.println("originalLanguageId: " + originalLanguageId);
        System.out.println("rentalDuration: " + rentalDuration);
        System.out.println("rentalRate: " + rentalRate);
        System.out.println("length: " + length);
        System.out.println("replacementCost: " + replacementCost);
        System.out.println("rating: " + rating);
        System.out.println("lastUpdate: " + lastUpdate);

        Timestamp ts;
        try {
            ts = Timestamp.valueOf(lastUpdate);
            System.out.println("Conversion lastUpdate réussie: " + ts.toString());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de conversion de lastUpdate: " + e.getMessage());
            ts = new Timestamp(System.currentTimeMillis());
            System.out.println("Utilisation du timestamp courant: " + ts.toString());
        }

        Film film = new Film();
        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(releaseYear);
        film.setLanguageId(languageId.byteValue());
        if (originalLanguageId != null) {
            film.setOriginalLanguageId(originalLanguageId.byteValue());
        }
        film.setRentalDuration(rentalDuration.byteValue());
        film.setRentalRate(rentalRate);
        film.setLength(length);
        film.setReplacementCost(replacementCost);
        film.setRating(rating);
        film.setLastUpdate(ts);
        // Si vous ne souhaitez pas gérer specialFeatures, on peut le laisser à sa
        // valeur par défaut dans la BDD
        // film.setSpecialFeatures("None");

        System.out.println("Film à ajouter: " + film.getTitle());
        try {
            filmRepository.save(film);
            System.out.println("Film sauvegardé avec succès, ID généré: " + film.getFilmId());
        } catch (Exception e) {
            System.err.println("Erreur lors de la sauvegarde du film: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return "Film Sauvegardé";
    }

    @PutMapping("/update/{id}")
    public @ResponseBody String updateFilm(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer releaseYear,
            @RequestParam Integer languageId,
            @RequestParam(required = false) Integer originalLanguageId,
            @RequestParam Integer rentalDuration,
            @RequestParam Double rentalRate,
            @RequestParam Integer length,
            @RequestParam Double replacementCost,
            @RequestParam String rating,
            @RequestParam String lastUpdate // format "yyyy-MM-dd HH:mm:ss"
    ) {
        System.out.println("=== UPDATE FILM ===");
        System.out.println("Film ID: " + id);
        if (!filmRepository.existsById(id)) {
            System.out.println("Film non trouvé pour mise à jour");
            return "Film non trouvé";
        }
        Film film = filmRepository.findById(id).orElse(null);
        if (film == null) {
            System.out.println("Film non trouvé (null)");
            return "Film non trouvé";
        }

        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(releaseYear);
        film.setLanguageId(languageId.byteValue());
        if (originalLanguageId != null) {
            film.setOriginalLanguageId(originalLanguageId.byteValue());
        }
        film.setRentalDuration(rentalDuration.byteValue());
        film.setRentalRate(rentalRate);
        film.setLength(length);
        film.setReplacementCost(replacementCost);
        film.setRating(rating);

        System.out.println("lastUpdate reçu (update): " + lastUpdate);
        Timestamp ts;
        try {
            ts = Timestamp.valueOf(lastUpdate);
            System.out.println("Conversion lastUpdate réussie (update): " + ts.toString());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de conversion de lastUpdate (update): " + e.getMessage());
            ts = new Timestamp(System.currentTimeMillis());
            System.out.println("Utilisation du timestamp courant (update): " + ts.toString());
        }
        film.setLastUpdate(ts);

        try {
            filmRepository.save(film);
            System.out.println("Film mis à jour avec succès: " + film.getTitle());
            return "Film Mis à jour";
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du film: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody String deleteFilm(@PathVariable Integer id) {
        System.out.println("=== DELETE FILM ===");
        System.out.println("Film ID: " + id);
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            System.out.println("Film supprimé");
            return "Film supprimé";
        } else {
            System.out.println("Film pas trouvé");
            return "Film pas trouvé";
        }
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("/getById")
    public @ResponseBody Film getFilmById(@RequestParam Integer id) {
        return filmRepository.findById(id).orElse(null);
    }

    @GetMapping("/allWithInventory")
    @ResponseBody
    public List<Map<String, Object>> getAllFilmsWithInventory() {
        
        List<Object[]> rawResults = filmRepository.findAllFilmsWithInventory();
        List<Map<String, Object>> finalList = new ArrayList<>();
        for (Object[] row : rawResults) {
            Map<String, Object> map = new HashMap<>();
            map.put("inventoryId", row[0]);
            map.put("title", row[1]);
            map.put("releaseYear", row[2]);
            map.put("rentalDuration", row[3]);
            map.put("description", row[4]);
            finalList.add(map);
        }
        return finalList;
    }

}
