package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.entities.Client;
import com.hawthorne_labs.springboot.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository ClientRepository;

    // Create or update Client
    public Client save(Client Client) {
        return ClientRepository.save(Client);
    }

    // Find Client by ID
    public Client findById(Long id) {
        return ClientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found with id: " + id));
    }

    // Return all Clients
    public List<Client> findAll() {
        return ClientRepository.findAll();
    }

    // Return only active Clients
    public List<Client> findActiveClients() {
        return ClientRepository.findByIsActiveTrue();
    }

    // Delete Client by ID
    public void deleteById(Long id) {
        ClientRepository.deleteById(id);
    }
}
