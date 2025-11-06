package com.hawthorne_labs.springboot.services;

import com.hawthorne_labs.springboot.dto.EmployeeDTO;
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
    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        return dtoMapper.toEmployeeDTO(employee);
    }

    // Return all employees
    public List<EmployeeDTO> findAllDTOs() {
        return employeeRepository.findAll()
                .stream()
                .map(dtoMapper::toEmployeeDTO)
                .toList();
    }
    // Return only active employees
    public List<EmployeeDTO> findActiveEmployees() {
        return employeeRepository.findByIsActiveTrue().stream()
                .map(dtoMapper::toEmployeeDTO)
                .toList();
    }

    // Delete employee by ID
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
