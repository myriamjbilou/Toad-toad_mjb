package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Inventory;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    @Query(value = """
                SELECT i.inventory_id, i.film_id, i.store_id, i.last_update
                FROM inventory i
                LEFT JOIN rental r ON i.inventory_id = r.inventory_id AND r.return_date IS NULL
                WHERE r.inventory_id IS NULL
            """, nativeQuery = true)
    List<Object[]> findAvailableInventory();

}