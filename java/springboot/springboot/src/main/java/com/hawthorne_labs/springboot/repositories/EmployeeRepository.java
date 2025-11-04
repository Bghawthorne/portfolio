package com.hawthorne_labs.springboot.repositories;

import com.hawthorne_labs.springboot.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  // Custom query to return only active employees
  List<Employee> findByIsActiveTrue();
}
