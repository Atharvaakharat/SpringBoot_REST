package org.example.controller;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Employee controller test.
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

  @Mock
  private EmployeeService employeeService;

  @InjectMocks
  private EmployeeController employeeController;

  /**
   * Sets up.
   */
  @BeforeEach
  public void setUp() {
    // Custom setup logic can go here if needed
  }

  /**
   * Test get all employees success.
   */
  @Test
  public void testGetAllEmployeesSuccess() {
    Employee emp1 = new Employee();
    emp1.setName("Alice");
    Employee emp2 = new Employee();
    emp2.setName("Bob");
    List<Employee> mockList = Arrays.asList(emp1, emp2);
    when(employeeService.getAllEmployees()).thenReturn(mockList);

    ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    assertEquals(2, response.getBody().size());
    assertEquals("Alice", response.getBody().get(0).getName());
  }

  /**
   * Test get all employees failure.
   */
  @Test
  public void testGetAllEmployeesFailure() {
    when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("DB error"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.getAllEmployees();
    });

    assertEquals("Database connection failed", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }

  /**
   * Test create employee success.
   */
  @Test
  public void testCreateEmployeeSuccess() {
    Employee emp = new Employee();
    emp.setName("John");
    when(employeeService.saveEmployee(any(Employee.class))).thenReturn(emp);

    ResponseEntity<Employee> response = employeeController.createEmployee(emp);

    assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals("John", response.getBody().getName());
  }

  /**
   * Test create employee failure.
   */
  @Test
  public void testCreateEmployeeFailure() {
    Employee emp = new Employee();
    when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new RuntimeException("Invalid data"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.createEmployee(emp);
    });

    assertEquals("Invalid employee data", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }

  /**
   * Test update employee success.
   */
  @Test
  public void testUpdateEmployeeSuccess() {
    Employee emp = new Employee();
    emp.setName("Updated");
    when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(emp);

    ResponseEntity<Employee> response = employeeController.updateEmployee(1L, emp);
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    assertEquals("Updated", response.getBody().getName());
  }

  /**
   * Test update employee not found.
   */
  @Test
  public void testUpdateEmployeeNotFound() {
    Employee emp = new Employee();
    when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
            .thenThrow(new NoSuchElementException("Not found"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.updateEmployee(1L, emp);
    });

    assertEquals("Employee not found", exception.getReason());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  /**
   * Test update employee failure.
   */
  @Test
  public void testUpdateEmployeeFailure() {
    Employee emp = new Employee();
    when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
            .thenThrow(new RuntimeException("Update failed"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.updateEmployee(1L, emp);
    });

    assertEquals("Failed to update employee", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }
}
