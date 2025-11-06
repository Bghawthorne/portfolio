package com.hawthorne_labs.springboot.controllers;

import com.hawthorne_labs.springboot.dto.EmployeeDTO;
import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllDTOs();
    }

    @GetMapping("/active")
    public List<EmployeeDTO> getActiveEmployees() {
        return employeeService.findActiveEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        try {
            EmployeeDTO emp = employeeService.findById(id);
            return ResponseEntity.ok(emp); // 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);

        // Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()      // /employees
                .path("/{id}")             // /employees/{id}
                .buildAndExpand(savedEmployee.getId())
                .toUri();

        return ResponseEntity.created(location) // sets 201 Created and Location header
                .body(savedEmployee); // include created entity in response body
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            employee.setId(id);
            return ResponseEntity.ok(employeeService.save(employee));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
