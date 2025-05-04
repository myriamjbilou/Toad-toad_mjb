package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Inventory;

// Interface qui permet d'accéder aux données de la table Inventory.
public interface InventoryDisponibleRepository extends CrudRepository<Inventory, Integer> {

    // Requête SQL native qui permet de récupérer les exemplaires disponibles (non
    // loués actuellement)
    //
    // - On fait une jointure entre "inventory" (les stocks) et "film" pour
    // récupérer des infos sur le film (title, release_year...)
    // - On filtre pour n'afficher que les inventories dont l’ID ne figure pas dans
    // la table rental
    // avec une return_date NULL (donc en cours de location)
    // Résultat : la liste des films disponibles à la location
    @Query(value = "SELECT i.inventory_id, f.film_id, f.title, f.release_year " +
            "FROM inventory i " +
            "JOIN film f ON f.film_id = i.film_id " +
            "WHERE i.inventory_id NOT IN (SELECT inventory_id FROM rental WHERE return_date IS NULL)", nativeQuery = true)

    // Méthode qui exécute la requête ci-dessus et retourne une liste d’objets non
    // mappés (Object[]),
    // car chaque ligne du résultat contient plusieurs colonnes issues de tables
    // différentes (inventory + film).
    List<Object[]> findAvailableInventoryWithFilmDetails();

}