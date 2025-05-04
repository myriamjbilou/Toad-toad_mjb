package com.toad.controllers;

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

import com.toad.entities.Inventory;
import com.toad.repositories.InventoryDisponibleRepository;
import com.toad.repositories.InventoryRepository;

@Controller
@RequestMapping(path = "/toad/inventory")
public class InventoryController {
    @Autowired // Injecte automatiquement l'objet InventoryRepository
    private InventoryRepository inventoryRepository;

    @Autowired // Injecte également le repository spécialisé pour les inventaires disponibles
    private InventoryDisponibleRepository inventoryDisponibleRepository;

    // Ajoute un nouvel inventaire dans la base
    @PostMapping(path = "/available/add")
    public @ResponseBody String addNewInventory(
            @RequestParam Integer film_id,
            @RequestParam Integer store_id,
            @RequestParam java.sql.Timestamp last_update) {

        // Création d'un nouvel objet Inventory
        Inventory inventory = new Inventory();
        inventory.setFilmId(film_id);
        inventory.setStoreId(store_id);
        inventory.setLastUpdate(last_update);

        // Enregistrement dans la base de données
        inventoryRepository.save(inventory);
        return "Inventaire Sauvegardé : " + +inventory.getInventoryId();
    }

    // Récupère tous les inventaires existants
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    // Récupère un inventaire en particulier à partir de son identifiant
    @GetMapping(path = "/getById")
    public @ResponseBody Inventory getInventoryById(@RequestParam Integer id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory != null) {
            // On crée un nouvel objet pour ne renvoyer que les infos utiles
            Inventory filteredInventory = new Inventory();
            filteredInventory.setInventoryId(inventory.getInventoryId());
            filteredInventory.setFilmId(inventory.getFilmId());
            filteredInventory.setStoreId(inventory.getStoreId());
            filteredInventory.setLastUpdate(inventory.getLastUpdate());
            return filteredInventory;
        }
        return null;
    }

    // Met à jour les informations d'un inventaire existant
    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateInventory(
            @PathVariable Integer id,
            @RequestParam Integer film_id,
            @RequestParam Integer store_id,
            @RequestParam java.sql.Timestamp last_update) {

        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        String message;

        if (inventory == null) {
            message = "Inventaire non trouvé";
        } else {
            inventory.setFilmId(film_id);
            inventory.setStoreId(store_id);
            inventory.setLastUpdate(last_update);

            inventoryRepository.save(inventory);
            message = "Inventaire Mis à Jour";
        }

        return message;
    }

    // Supprime un inventaire par son identifiant
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteInventory(@PathVariable Integer id) {
        inventoryRepository.deleteById(id);
        return "Inventaire Supprimé";
    }

    // Récupère tous les inventaires disponibles (c’est-à-dire non loués)
    @GetMapping("/available")
    @ResponseBody
    public List<Map<String, Object>> getAvailableInventory() {
        List<Object[]> results = inventoryRepository.findAvailableInventory();
        List<Map<String, Object>> response = new ArrayList<>();

        // On transforme chaque ligne de résultat en un dictionnaire clé-valeur
        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("inventoryId", row[0]);
            item.put("filmId", row[1]);
            item.put("storeId", row[2]);
            item.put("lastUpdate", row[3]);
            response.add(item);
        }

        return response;
    }

    // Version enrichie de l'inventaire disponible avec les titres des films
    @GetMapping("/available/details")
    public @ResponseBody List<Map<String, Object>> getAvailableInventoryWithDetails() {
        List<Object[]> rawResults = inventoryDisponibleRepository.findAvailableInventoryWithFilmDetails();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            Map<String, Object> item = new HashMap<>();
            item.put("inventoryId", row[0]);
            item.put("filmId", row[1]);
            item.put("title", row[2]);
            item.put("releaseYear", row[3]);

            result.add(item);
        }

        return result;
        
    }
    // Méthode utilitaire interne qui convertit dynamiquement un objet numérique en Integer
    private Integer convertToInteger(Object value) {
        if (value instanceof Byte) {
            return ((Byte) value).intValue();
        } else if (value instanceof Short) {
            return ((Short) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            throw new IllegalArgumentException("Type inattendu : " + value.getClass().getName());
        }
    }

}
