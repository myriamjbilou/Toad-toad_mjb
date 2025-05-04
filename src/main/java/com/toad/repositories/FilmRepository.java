package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Film;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FilmRepository extends CrudRepository<Film, Integer> {
    
    // Requête native SQL personnalisée qui récupère tous les films avec des informations supplémentaires :
    // - le titre, l’année de sortie, la durée de location, la description
    // - et surtout le nombre total d’exemplaires en stock (totalCopies)
    // La jointure s’effectue entre les tables "film" et "inventory" sur l’attribut film_id.
    // GROUP BY est obligatoire ici pour pouvoir utiliser COUNT() correctement.
    @Query(value = "SELECT f.film_id, f.title, f.release_year, f.rental_duration, f.description, COUNT(i.inventory_id) AS totalCopies "
            +
            "FROM film f " +
            "JOIN inventory i ON i.film_id = f.film_id " +
            "GROUP BY f.film_id, f.title, f.release_year, f.rental_duration, f.description", nativeQuery = true)

    // Cette méthode exécute la requête ci-dessus et retourne une liste de tableaux d’objets (Object[]),
    // car le résultat n’est pas une entité Film classique, mais une ligne de données avec plusieurs colonnes spécifiques.
    List<Object[]> findAllFilmsWithInventory();
}