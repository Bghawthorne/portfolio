package com.hawthorne_labs.springboot;

import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.repositories.EmployeeRepository;
import com.hawthorne_labs.springboot.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .email("john.doe@example.com")
                .baseRate(new BigDecimal("25.50"))
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetEmployeeById_Found() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findById(1L);

        Assertions.assertEquals("John", result.getFirstName());
        Mockito.verify(employeeRepository).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> employeeService.findById(2L));
    }

    @Test
    void testCreateEmployee() {
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee saved = employeeService.save(employee);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Doe", saved.getLastName());
    }
}
