package com.toad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Staff;

// Ce repository permet d’effectuer des opérations de base (CRUD) sur les objets de type Staff
// grâce à l’héritage de CrudRepository<Staff, Integer>
// Integer correspond au type de la clé primaire (staff_id)
public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Staff findByEmail(String email);
}
