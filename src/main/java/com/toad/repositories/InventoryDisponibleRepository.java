package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Inventory;

public interface InventoryDisponibleRepository extends CrudRepository<Inventory, Integer> {

    @Query(value = "SELECT i.inventory_id, f.film_id, f.title, f.release_year FROM inventory i JOIN film f ON f.film_id = i.film_id WHERE i.inventory_id NOT IN (SELECT inventory_id FROM rental WHERE return_date IS NULL)", nativeQuery = true)
    List<Object[]> findAvailableInventoryWithFilmDetails();

}