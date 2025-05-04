package com.toad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toad.entities.Director;
// Interface DirectorRepository : permet d’accéder à la base pour manipuler les objets Director.
public interface DirectorRepository extends JpaRepository<Director, Integer> {
    
    // Requête SQL personnalisée (nativeQuery = true => on utilise du SQL classique
    // Récupérer les informations d’un réalisateur (nom, prénom, ID)
    // et compter combien de films il a réalisés (via une jointure sur la table intermédiaire film_director)
    @Query(value = "SELECT d.director_id, d.nom, d.prenom, COUNT(f.film_id) AS nombre_films " +
                   "FROM director d " +
                   "LEFT JOIN film_director f ON d.director_id = f.director_id " +
                   "WHERE d.nom = :nom AND d.prenom = :prenom " +
                   "GROUP BY d.director_id, d.nom", nativeQuery = true)
    List<Object[]> findDirectorWithFilmCount(@Param("nom") String nom, @Param("prenom") String prenom);
}