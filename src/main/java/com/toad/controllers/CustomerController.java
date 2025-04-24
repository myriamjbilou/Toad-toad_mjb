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

@Controller
@RequestMapping(path = "/toad/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // -------------------- CREATE --------------------
    // On utilise @RequestParam pour tout, y compris customer_id
    // Si la BDD génère l’ID automatiquement (strategy=GenerationType.AUTO),
    // tu peux simplement ignorer customer_id ou ne PAS le passer en paramètre.
    @PostMapping(path="/add")
    public @ResponseBody String addNewCustomer (
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
        @RequestParam int age
    ) {
        // CREATION D'UN CLIENT
        Customer newCustomer = new Customer();

        // Si tu veux vraiment imposer un customer_id précis :
        if (customer_id != null) {
            newCustomer.setCustomerId(customer_id);
        }
        // Sinon, laisse JPA générer l’ID automatiquement

        newCustomer.setStoreId(store_id);
        newCustomer.setFirstName(first_name);
        newCustomer.setLastName(last_name);
        newCustomer.setEmail(email);
        newCustomer.setAddressId(address_id);
        newCustomer.setActive(active);

        // Selon ta logique, tu peux décider quel champ correspond à "create_update" vs "createDate"
        newCustomer.setCreateDate(create_date);

        newCustomer.setLastUpdate(last_update);
        newCustomer.setPassword(password);
        newCustomer.setAge(age);

        customerRepository.save(newCustomer);

        return "Client créé";
    }

    // -------------------- UPDATE --------------------
    // Ici, on conserve la logique pathVariable pour l'ID
    // (=> URL : /toad/customer/update/123?storeId=...&email=...)
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
        Customer customer = customerRepository.findById(customer_id).orElse(null);
        if (customer == null) {
            return "Erreur lors de la mise à jour du client (inexistant)";
        }

        // MISE A JOUR DU CLIENT
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

        customerRepository.save(customer);
        return "Mise à jour du Client";
    }

    // -------------------- DELETE --------------------
    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody String deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
        return "Customer supprimé";
    }

    // -------------------- GET ALL --------------------
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // -------------------- GET BY EMAIL --------------------
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
