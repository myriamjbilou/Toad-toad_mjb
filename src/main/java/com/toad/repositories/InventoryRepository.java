package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Inventory;

// Interface de repository qui permet d’effectuer des opérations CRUD (Create, Read, Update, Delete)
// sur la table "inventory" en base de données. Elle hérite de CrudRepository.
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    // Requête SQL native permettant de récupérer les stocks (inventaires) qui sont
    // actuellement disponibles,
    // c’est-à-dire les DVDs qui ne sont pas en cours de location.

    @Query(value = """
                SELECT i.inventory_id, i.film_id, i.store_id, i.last_update
                FROM inventory i
                LEFT JOIN rental r ON i.inventory_id = r.inventory_id AND r.return_date IS NULL
                WHERE r.inventory_id IS NULL
            """, nativeQuery = true)

    // Méthode exécutant la requête SQL ci-dessus.
    // Elle retourne une liste d'objets (Object[]) car la requête ne mappe pas
    // directement une entité complète,
    // mais plusieurs champs (ID, film, magasin, date) issus de la jointure.
    List<Object[]> findAvailableInventory();

}