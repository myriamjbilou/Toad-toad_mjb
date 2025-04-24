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
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryDisponibleRepository inventoryDisponibleRepository;

    @PostMapping(path = "/available/add")
    public @ResponseBody String addNewInventory(
            @RequestParam Integer film_id,
            @RequestParam Integer store_id,
            @RequestParam java.sql.Timestamp last_update) {

        Inventory inventory = new Inventory();
        inventory.setFilmId(film_id);
        inventory.setStoreId(store_id);
        inventory.setLastUpdate(last_update);

        inventoryRepository.save(inventory);
        return "Inventaire Sauvegardé : " + +inventory.getInventoryId();
    }

    // film_id:1
    // store_id:1
    // last_update:2024-01-01 00:00:00.0

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody Inventory getInventoryById(@RequestParam Integer id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory != null) {
            Inventory filteredInventory = new Inventory();
            filteredInventory.setInventoryId(inventory.getInventoryId());
            filteredInventory.setFilmId(inventory.getFilmId());
            filteredInventory.setStoreId(inventory.getStoreId());
            filteredInventory.setLastUpdate(inventory.getLastUpdate());
            return filteredInventory;
        }
        return null;
    }

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

    // film_id:1
    // store_id:1
    // last_update:2024-01-01 00:00:00.0

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteInventory(@PathVariable Integer id) {
        inventoryRepository.deleteById(id);
        return "Inventaire Supprimé";
    }

    @GetMapping("/available")
    @ResponseBody
    public List<Map<String, Object>> getAvailableInventory() {
        List<Object[]> results = inventoryRepository.findAvailableInventory();
        List<Map<String, Object>> response = new ArrayList<>();

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

    /**
     * Méthode utilitaire pour convertir dynamiquement un objet en Integer.
     */
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
