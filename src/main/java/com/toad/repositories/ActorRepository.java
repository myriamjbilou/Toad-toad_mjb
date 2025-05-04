package com.toad.repositories;

import org.springframework.data.repository.CrudRepository;

import com.toad.entities.Actor;

// Interface permettant d'accéder à la table "actor" en base de données
// Elle hérite de CrudRepository, ce qui donne automatiquement les méthodes de base
public interface ActorRepository extends CrudRepository<Actor, Integer> {

}