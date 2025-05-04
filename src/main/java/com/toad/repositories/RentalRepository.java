package com.toad.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Rental;

// Cette interface permet d'effectuer les opérations CRUD (Create, Read, Update, Delete) 
// sur la table "rental" grâce à Spring Data JPA.
// Elle hérite de CrudRepository et est automatiquement prise en charge par Spring.
public interface RentalRepository extends CrudRepository<Rental, Integer> {

    // Cette méthode personnalisée permet de récupérer toutes les locations (Rental)
    // associées à un DVD (inventoryId) qui n'ont pas encore été retournées
    // (returnDate = null).
    // → Elle est utile pour savoir si un DVD est déjà en cours de location.
    List<Rental> findByInventoryIdAndReturnDateIsNull(Integer inventoryId);

}