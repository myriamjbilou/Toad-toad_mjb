package com.toad.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Cette classe représente une ligne de l'inventaire (un DVD dans un magasin)
@Entity
public class Inventory {
  @Id // Indique que c'est la clé primaire
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
  @Column(name = "inventory_id") // Nom de la colonne SQL associée
  private Integer inventoryId;

  @Column(name = "film_id")
  private Integer filmId;

  @Column(name = "store_id")
  private Integer storeId;

  @Column(name = "last_update")
  private Timestamp lastUpdate;

  // Getters et Setters
  // Permettent de lire ou modifier les valeurs des attributs (encapsulation)
  public Integer getInventoryId() {
    return inventoryId;
  }

  public void setInventoryId(Integer inventoryId) {
    this.inventoryId = inventoryId;
  }

  public Integer getFilmId() {
    return filmId;
  }

  public void setFilmId(Integer filmId) {
    this.filmId = filmId;
  }

  public Integer getStoreId() {
    return storeId;
  }

  public void setStoreId(Integer storeId) {
    this.storeId = storeId;
  }

  public Timestamp getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Timestamp lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}