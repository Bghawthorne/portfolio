package com.hawthorne_labs.springboot.repositories;
import com.hawthorne_labs.springboot.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
