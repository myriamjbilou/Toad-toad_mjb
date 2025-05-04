package com.toad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Customer;

// Interface CustomerRepository : elle sert à faire le lien entre l’entité Customer et la base de données
// Elle hérite de CrudRepository, donc on a accès aux méthodes de base : save(), findById(), findAll(), deleteById(), etc.
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    // va chercher un client en base de données dont l’adresse e-mail correspond à la valeur fournie
    // Si aucun client n’est trouvé, Spring retourne null
    Customer findByEmail(String email);

}