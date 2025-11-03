package com.hawthorne_labs.springboot;

import com.hawthorne_labs.springboot.entities.Client;
import com.hawthorne_labs.springboot.repositories.ClientRepository;
import com.hawthorne_labs.springboot.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------------- SAVE client ----------------
    @Test
    public void testSaveClient() {
        Client client = Client.builder().firstName("John").lastName("Doe").build();
        Client savedClient = Client.builder().id(1L).firstName("John").lastName("Doe").build();

        when(clientRepository.save(client)).thenReturn(savedClient);

        Client result = clientService.save(client);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(clientRepository, times(1)).save(client);
    }

    // ---------------- FIND BY ID ----------------
    @Test
    public void testFindById_Found() {
        Client client = Client.builder().id(1L).firstName("John").build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> clientService.findById(2L));

        assertEquals("Client not found with id: 2", exception.getMessage());
        verify(clientRepository, times(1)).findById(2L);
    }

    // ---------------- FIND ALL ----------------
    @Test
    public void testFindAll() {
        Client c1 = Client.builder().id(1L).firstName("John").build();
        Client c2 = Client.builder().id(2L).firstName("Jane").build();
        List<Client> clients = Arrays.asList(c1, c2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(clientRepository, times(1)).findAll();
    }

    // ---------------- FIND ACTIVE CLIENTS ----------------
    @Test
    public void testFindActiveClients() {
        Client c1 = Client.builder().id(1L).firstName("John").build();
        List<Client> clients = Arrays.asList(c1);

        when(clientRepository.findByIsActiveTrue()).thenReturn(clients);

        List<Client> result = clientService.findActiveClients();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(clientRepository, times(1)).findByIsActiveTrue();
    }

    // ---------------- DELETE CLIENT ----------------
    @Test
    public void testDeleteById() {
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteById(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }
}
