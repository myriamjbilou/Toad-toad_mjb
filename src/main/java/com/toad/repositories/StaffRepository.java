package com.toad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Staff findByEmail(String email);
}
