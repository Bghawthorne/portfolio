package com.hawthorne_labs.springboot.repositories;

import com.hawthorne_labs.springboot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Custom query to return only active clients
    List<Client> findByIsActiveTrue();
}
