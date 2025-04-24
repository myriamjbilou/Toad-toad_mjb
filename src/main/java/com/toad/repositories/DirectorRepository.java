package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toad.entities.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
    @Query(value = "SELECT d.director_id, d.nom, d.prenom, COUNT(f.film_id) AS nombre_films " +
                   "FROM director d " +
                   "LEFT JOIN film_director f ON d.director_id = f.director_id " +
                   "WHERE d.nom = :nom AND d.prenom = :prenom " +
                   "GROUP BY d.director_id, d.nom", nativeQuery = true)
    List<Object[]> findDirectorWithFilmCount(@Param("nom") String nom, @Param("prenom") String prenom);
}