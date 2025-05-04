//Contrôleur REST pour gérer les clients dans TOAD.
// Ce fichier permet d’ajouter, modifier, supprimer et consulter des clients via des requêtes HTTP.
// Chaque méthode correspond à une opération CRUD (Create, Read, Update, Delete)
package com.toad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toad.entities.Customer;
import com.toad.repositories.CustomerRepository;

// Annotation indiquant à Spring que cette classe est un contrôleur web (accès via HTTP)
@Controller
@RequestMapping(path = "/toad/customer")
public class CustomerController {

    // Injection automatique du repository pour accéder à la base de données client
    @Autowired
    private CustomerRepository customerRepository;

    // Ajoute un nouveau client dans la base

    // Endpoint : POST /toad/customer/add avec les données du client dans les
    // paramètres
    @PostMapping(path = "/add")
    public @ResponseBody String addNewCustomer(
            @RequestParam(required = false) Integer customer_id,
            @RequestParam Integer store_id,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String email,
            @RequestParam Integer address_id,
            @RequestParam int active,
            @RequestParam java.sql.Timestamp create_date,
            @RequestParam java.sql.Timestamp last_update,
            @RequestParam String password,
            @RequestParam int age) {

        // On crée un objet Customer vide pour y injecter les données reçues
        Customer newCustomer = new Customer();

        // Si l'ID a été envoyé, on l'utilise (sinon il sera auto-généré par la base)
        if (customer_id != null) {
            newCustomer.setCustomerId(customer_id);
        }

        // On enregistre toutes les infos dans l'objet Customer
        newCustomer.setStoreId(store_id);
        newCustomer.setFirstName(first_name);
        newCustomer.setLastName(last_name);
        newCustomer.setEmail(email);
        newCustomer.setAddressId(address_id);
        newCustomer.setActive(active);
        newCustomer.setCreateDate(create_date);
        newCustomer.setLastUpdate(last_update);
        newCustomer.setPassword(password);
        newCustomer.setAge(age);

        // Sauvegarde de l'objet Customer dans la base de données
        customerRepository.save(newCustomer);

        // Message de succès retourné au client
        return "Client créé";
    }

    // UPDATE

    // Cette méthode permet de modifier les infos d’un client existant.
    // Requête attendue : PUT /toad/customer/update/{customerId} avec les nouveaux
    // champs en paramètres
    @PutMapping(path = "/update/{customerId}")
    public @ResponseBody String updateRepository(
            @PathVariable Integer customer_id,
            @RequestParam Integer store_id,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String email,
            @RequestParam Integer address_id,
            @RequestParam int active,
            @RequestParam java.sql.Timestamp create_date,
            @RequestParam java.sql.Timestamp last_update,
            @RequestParam String password,
            @RequestParam int age

    ) {
        // On cherche si un client avec cet ID existe
        Customer customer = customerRepository.findById(customer_id).orElse(null);

        // Si le client n'existe pas, on retourne un message d'erreur
        if (customer == null) {
            return "Erreur lors de la mise à jour du client (inexistant)";
        }

        // Sinon, on modifie toutes ses infos
        customer.setStoreId(store_id);
        customer.setFirstName(first_name);
        customer.setLastName(last_name);
        customer.setEmail(email);
        customer.setAddressId(address_id);
        customer.setActive(active);
        customer.setCreateDate(create_date);
        customer.setLastUpdate(last_update);
        customer.setPassword(password);
        customer.setAge(age);

        // On sauvegarde les modifications
        customerRepository.save(customer);
        return "Mise à jour du Client";
    }

    // DELETE

    // Supprime un client selon son ID
    // Appelée avec DELETE /toad/customer/delete/ID
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
        return "Customer supprimé";
    }

    // GET ALL, Affichage de tous les clients

    // Retourne tous les clients enregistrés en base
    // Appelée avec GET /toad/customer/all
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // GET BY EMAIL, recherche d'un client par email 

    // Recherche un client avec son email
    @GetMapping("/getByEmail")
    @ResponseBody
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
