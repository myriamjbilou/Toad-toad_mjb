package com.toad.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Cette classe représente une location de DVD (entité liée à la table "rental")
@Entity
public class Rental {

    // Clé primaire auto-incrémentée (générée par la BDD)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer rental_id; 

    @Column(name = "rental_date")
    private String rental_date;

    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "customer_id")
    private Integer customer_id;

    @Column(name = "return_date")
    private String returnDate; 

    @Column(name = "staff_id")
    private Integer staff_id; 

    @Column(name = "last_update")
    private String last_update;

    // Getters and Setters
    // Permettent d'accéder aux champs privés et de les modifier proprement
    
    public Integer getRentalId() {
        return rental_id;
    }

    public void setRentalId(Integer rental_id) {
        this.rental_id = rental_id;
    }

    public String getRentalDate() {
        return rental_date;
    }

    public void setRentalDate(String rental_date) {
        this.rental_date = rental_date;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getStaffId() {
        return staff_id;
    }

    public void setStaffId(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public String getLastUpdate() {
        return last_update;
    }

    public void setLastUpdate(String last_update) {
        this.last_update = last_update;
    }
}
