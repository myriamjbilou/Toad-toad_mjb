package com.toad.entities;

// Importation des annotations nécessaires pour la persistance JPA (mapping objet-relationnel)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Cette classe représente une entité JPA liée à la table "customer" dans la base de données
@Entity
@Table(name = "customer")
public class Customer {

    // Clé primaire de l'entité, auto-incrémentée (IDENTITY = auto-incrément côté
    // BDD)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    // Identifiant du magasin associé au client
    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address_id")
    private Integer addressId;

    // État d’activation du client : 1 = actif, 0 = inactif
    @Column(name = "active")
    private int active;

    // Date de création du client (java.sql.Timestamp pour coller au type SQL)
    @Column(name = "create_date")
    private java.sql.Timestamp createDate;

    @Column(name = "last_update")
    private java.sql.Timestamp lastUpdate;

    // Mot de passe du client (type VARCHAR en base, donc String en Java)
    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    // Getters et setters:
    // Permettent de lire (get) ou modifier (set) les champs privés de l'entité,
    // tout en respectant le principe d'encapsulation.
    // Obligatoires pour que JPA puisse gérer automatiquement les données de la
    // base.

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public java.sql.Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Timestamp createDate) {
        this.createDate = createDate;
    }

    public java.sql.Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.sql.Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
