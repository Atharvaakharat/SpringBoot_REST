
package org.example.controller;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Employee controller test.
 */
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
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test get all employees success.
   */
  @Test
  public void testGetAllEmployees_Success() {
    List<Employee> employees = Arrays.asList(new Employee(), new Employee());
    when(employeeService.getAllEmployees()).thenReturn(employees);

    ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  /**
   * Test create employee success.
   */
  @Test
  public void testCreateEmployee_Success() {
    Employee employee = new Employee();
    employee.setName("John");
    when(employeeService.saveEmployee(employee)).thenReturn(employee);

    ResponseEntity<Employee> response = employeeController.createEmployee(employee);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("John", response.getBody().getName());
  }

  /**
   * Test update employee exception.
   */
  @Test
  public void testUpdateEmployee_Exception() {
    Employee employee = new Employee();
    when(employeeService.updateEmployee(anyLong(), any(Employee.class)))
            .thenThrow(new RuntimeException("Update failed"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.updateEmployee(1L, employee);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Error updating employee", exception.getReason());
  }
}
