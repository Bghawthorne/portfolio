package com.hawthorne_labs.springboot.controllers;

import com.hawthorne_labs.springboot.entities.Client;
import com.hawthorne_labs.springboot.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService ClientService;

    @GetMapping
    public List<Client> getAllClients() {
        return ClientService.findAll();
    }

    @GetMapping("/active")
    public List<Client> getActiveClients() {
        return ClientService.findActiveClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        try {
            Client emp = ClientService.findById(id);
            return ResponseEntity.ok(emp); // 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client Client) {
        Client savedClient = ClientService.save(Client);

        // Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()      // /Clients
                .path("/{id}")             // /Clients/{id}
                .buildAndExpand(savedClient.getId())
                .toUri();

        return ResponseEntity.created(location) // sets 201 Created and Location header
                .body(savedClient); // include created entity in response body
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client Client) {
        try {
            Client.setId(id);
            return ResponseEntity.ok(ClientService.save(Client));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        ClientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
