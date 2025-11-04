package com.hawthorne_labs.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawthorne_labs.springboot.controllers.EmployeeController;
import com.hawthorne_labs.springboot.entities.Employee;
import com.hawthorne_labs.springboot.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- GET all employees ----------------
    @Test
    public void testGetAllEmployees() throws Exception {
        Employee emp1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .email("john@example.com")
                .baseRate(new BigDecimal("25.00"))
                .build();

        Employee emp2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .isActive(true)
                .email("jane@example.com")
                .baseRate(new BigDecimal("30.00"))
                .build();

        List<Employee> employees = Arrays.asList(emp1, emp2);
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    // ---------------- GET employee by ID ----------------
    @Test
    public void testGetEmployeeById() throws Exception {
        Employee emp = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .email("john@example.com")
                .baseRate(new BigDecimal("25.00"))
                .build();

        when(employeeService.findById(1L)).thenReturn(emp);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // ---------------- GET employee by ID NOT FOUND ----------------
    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
        when(employeeService.findById(2L))
                .thenThrow(new NoSuchElementException("Employee not found with id: 2"));

        mockMvc.perform(get("/api/employees/2"))
                .andExpect(status().isNotFound());
    }

    // ---------------- CREATE employee ----------------
    @Test
    public void testCreateEmployee() throws Exception {
        Employee emp = Employee.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .isActive(true)
                .email("alice@example.com")
                .baseRate(new BigDecimal("28.00"))
                .build();

        Employee savedEmp = Employee.builder()
                .id(3L)
                .firstName("Alice")
                .lastName("Johnson")
                .isActive(true)
                .email("alice@example.com")
                .baseRate(new BigDecimal("28.00"))
                .build();

        when(employeeService.save(any(Employee.class))).thenReturn(savedEmp);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }
}
