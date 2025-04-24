package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Film;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FilmRepository extends CrudRepository<Film, Integer> {

    @Query(value = "SELECT f.film_id, f.title, f.release_year, f.rental_duration, f.description, COUNT(i.inventory_id) AS totalCopies "
            +
            "FROM film f " +
            "JOIN inventory i ON i.film_id = f.film_id " +
            "GROUP BY f.film_id, f.title, f.release_year, f.rental_duration, f.description", nativeQuery = true)

    List<Object[]> findAllFilmsWithInventory();
}