// Indique que cette classe fait partie du package "entities" (donc c’est un modèle lié à la base de données)
package com.toad.entities;

// Importation des annotations JPA (Jakarta Persistence API) pour mapper la classe à une table en base de données
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Annotation qui indique que cette classe représente une table en base de données
@Entity
public class Actor {

    // Clé primaire de la table Actor. Cette valeur est générée automatiquement
    // (auto-incrémentée)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actor_id;
    private String first_name;
    private String last_name;
    private String last_update;

    // Getters and Setters
    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
