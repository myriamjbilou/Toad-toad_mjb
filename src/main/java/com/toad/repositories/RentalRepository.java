package com.toad.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Rental;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface RentalRepository extends CrudRepository<Rental, Integer> {

    List<Rental> findByInventoryIdAndReturnDateIsNull(Integer inventoryId);


}