package com.hawthorne_labs.springboot.repositories;

import com.hawthorne_labs.springboot.entities.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {
}
