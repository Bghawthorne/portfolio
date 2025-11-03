package com.hawthorne_labs.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawthorne_labs.springboot.controllers.ClientController;
import com.hawthorne_labs.springboot.entities.Client;
import com.hawthorne_labs.springboot.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- GET all clients ----------------
    @Test
    public void testGetAllClients() throws Exception {
        Client c1 = Client.builder().id(1L).firstName("John").lastName("Doe").build();
        Client c2 = Client.builder().id(2L).firstName("Jane").lastName("Smith").build();
        List<Client> clients = Arrays.asList(c1, c2);

        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get("/api/Clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    // ---------------- GET active clients ----------------
    @Test
    public void testGetActiveClients() throws Exception {
        Client c1 = Client.builder().id(1L).firstName("John").lastName("Doe").build();
        List<Client> clients = Arrays.asList(c1);

        when(clientService.findActiveClients()).thenReturn(clients);

        mockMvc.perform(get("/api/Clients/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    // ---------------- GET client by ID ----------------
    @Test
    public void testGetClientById() throws Exception {
        Client c = Client.builder().id(1L).firstName("John").lastName("Doe").build();

        when(clientService.findById(1L)).thenReturn(c);

        mockMvc.perform(get("/api/Clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.id").value(1));
    }

    // ---------------- GET client by ID NOT FOUND ----------------
    @Test
    public void testGetClientById_NotFound() throws Exception {
        when(clientService.findById(2L)).thenThrow(new NoSuchElementException("Client not found with id: 2"));

        mockMvc.perform(get("/api/Clients/2"))
                .andExpect(status().isNotFound());
    }

    // ---------------- CREATE client ----------------
    @Test
    public void testCreateClient() throws Exception {
        Client c = Client.builder().firstName("Alice").lastName("Johnson").build();
        Client savedClient = Client.builder().id(3L).firstName("Alice").lastName("Johnson").build();

        when(clientService.save(any(Client.class))).thenReturn(savedClient);

        mockMvc.perform(post("/api/Clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/Clients/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    // ---------------- UPDATE client ----------------
    @Test
    public void testUpdateClient() throws Exception {
        Client c = Client.builder().firstName("Bob").lastName("Marley").build();
        Client updatedClient = Client.builder().id(1L).firstName("Bob").lastName("Marley").build();

        when(clientService.save(any(Client.class))).thenReturn(updatedClient);

        mockMvc.perform(put("/api/Clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Bob"));
    }

    // ---------------- UPDATE client NOT FOUND ----------------
    @Test
    public void testUpdateClient_NotFound() throws Exception {
        Client c = Client.builder().firstName("Bob").lastName("Marley").build();
        when(clientService.save(any(Client.class))).thenThrow(new NoSuchElementException());

        mockMvc.perform(put("/api/Clients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isNotFound());
    }

    // ---------------- DELETE client ----------------
    @Test
    public void testDeleteClient() throws Exception {
        doNothing().when(clientService).deleteById(1L);

        mockMvc.perform(delete("/api/Clients/1"))
                .andExpect(status().isOk());
    }
}
