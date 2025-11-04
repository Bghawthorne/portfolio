package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Create or update employee
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Find employee by ID
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));
    }

    // Return all employees
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    // Return only active employees
    public List<Employee> findActiveEmployees() {
        return employeeRepository.findByIsActiveTrue();
    }

    // Delete employee by ID
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
