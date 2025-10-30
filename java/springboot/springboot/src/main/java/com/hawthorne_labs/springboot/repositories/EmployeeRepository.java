package com.hawthrone_labs.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hawthorne_labs.springboot.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
  {}
