package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.dto.ClientDTO;
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
    public ClientDTO findById(Long id) {
        Client client = ClientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found with id: " + id));
        return DtoMapper.toClientDTO(client);
    }

    // Return all Clients
    public List<ClientDTO> findAll() {
        return ClientRepository.findAll()
                .stream()
                .map(DtoMapper::toClientDTO)
                .toList();
    }

    // Return only active Clients
    public List<ClientDTO> findActiveClients() {

        return ClientRepository.findByIsActiveTrue()
                .stream()
                .map(DtoMapper::toClientDTO)
                .toList();
    }

    // Delete Client by ID
    public void deleteById(Long id) {
        ClientRepository.deleteById(id);
    }
}
