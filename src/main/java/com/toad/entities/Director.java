package com.toad.entities;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
@Entity // Indique à JPA (via Hibernate) que cette classe représente une table dans la base de données
public class Director {

    @Id // Clé primaire de la table
    @GeneratedValue(strategy = GenerationType.AUTO) // L'ID sera généré automatiquement
    private Integer director_id;  // Identifiant unique du réalisateur (clé primaire)
 
    private String nom;
 
    private String prenom;
 
    private String date_naissance; 
 
    private String nationnalite; 
 
    // Getters and Setters
    // Utilisés pour accéder ou modifier les attributs privés.
    // Obligatoires pour que JPA puisse lire/écrire les valeurs dans la base.
    public Integer getdirector_id() {
        return director_id;
    }
 
    public void setdirector_id(Integer director_id) {
        this.director_id = director_id;
    }
 
    public String getnom() {
        return nom;
    }
 
    public void setnom(String nom) {
        this.nom = nom;
    }
 
    public String getprenom() {
        return prenom;
    }
 
    public void setprenom(String prenom) {
        this.prenom = prenom;
    }
 
    public String  getdate_naissance() {
        return date_naissance;
    }
 
    public void setdate_naissance(String  date_naissance) {
        this.date_naissance = date_naissance;
    }
 
    public String getnationnalite() {
        return nationnalite;
    }
 
    public void setnationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
    }
 
}
 