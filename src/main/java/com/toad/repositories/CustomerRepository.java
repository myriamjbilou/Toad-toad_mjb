package com.toad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Customer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

 // Renvoie le Customer correspondant à l’email, ou null s’il n’existe pas
    Customer findByEmail(String email);

}