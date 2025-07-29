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

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

  @Mock
  private EmployeeService employeeService;

  @InjectMocks
  private EmployeeController employeeController;

  private Employee emp1;
  private Employee emp2;

  @BeforeEach
  public void setUp() {
    // No extra setup needed currently

    emp1 = new Employee();
    emp1.setName("Alice");
    emp1.setPosition("Developer");
    emp1.setMobileNo("1234567890");
    emp1.setAddress("Pune");

    emp2 = new Employee();
    emp2.setName("Bob");
    emp2.setPosition("Tester");
    emp2.setMobileNo("9876543210");
    emp2.setAddress("Mumbai");


  }

  @Test
  public void testGetAllEmployeesSuccess() {
    emp1.setName("Alice");
    emp1.setPosition("Developer");
    emp1.setMobileNo("1234567890");
    emp1.setAddress("Pune");

    emp2.setName("Bob");
    emp2.setPosition("Tester");
    emp2.setMobileNo("9876543210");
    emp2.setAddress("Mumbai");

    List<Employee> mockList = Arrays.asList(emp1, emp2);
    when(employeeService.getAllEmployees()).thenReturn(mockList);

    ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());

    Employee first = response.getBody().get(0);
    assertEquals("Alice", first.getName());
    assertEquals("Developer", first.getPosition());
    assertEquals("1234567890", first.getMobileNo());
    assertEquals("Pune", first.getAddress());
  }

  @Test
  public void testGetAllEmployeesFailure() {
    when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("DB error"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.getAllEmployees();
    });

    assertEquals(ResponseStatusException.class, exception.getClass());
    assertEquals("Database connection failed", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }

  @Test
  public void testGetAllEmployees_ReturningNull() {
    when(employeeService.getAllEmployees()).thenReturn(null);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.getAllEmployees();
    });

    assertEquals(ResponseStatusException.class, exception.getClass());
    assertEquals("Database connection failed", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }

  @Test
  public void testCreateEmployeeSuccess() {
    emp1.setName("John");
    emp1.setPosition("Engineer");
    emp1.setMobileNo("1112223333");
    emp1.setAddress("Delhi");

    when(employeeService.saveEmployee(any(Employee.class))).thenReturn(emp1);

    ResponseEntity<Employee> response = employeeController.createEmployee(emp1);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("John", response.getBody().getName());
    assertEquals("Engineer", response.getBody().getPosition());
    assertEquals("1112223333", response.getBody().getMobileNo());
    assertEquals("Delhi", response.getBody().getAddress());
  }

  @Test
  public void testCreateEmployeeFailure() {
    when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new RuntimeException("Invalid data"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.createEmployee(emp1);
    });

    assertEquals(ResponseStatusException.class, exception.getClass());
    assertEquals("Invalid employee data", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }

  @Test
  public void testUpdateEmployeeSuccess() {
    emp1.setName("Updated");
    emp1.setPosition("Lead");
    emp1.setMobileNo("9998887777");
    emp1.setAddress("Hyderabad");

    when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(emp1);

    ResponseEntity<Employee> response = employeeController.updateEmployee(1L, emp1);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Updated", response.getBody().getName());
    assertEquals("Lead", response.getBody().getPosition());
    assertEquals("9998887777", response.getBody().getMobileNo());
    assertEquals("Hyderabad", response.getBody().getAddress());
  }

  @Test
  public void testUpdateEmployeeNotFound() {
    when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
            .thenThrow(new NoSuchElementException("Not found"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.updateEmployee(1L, emp1);
    });

    assertEquals(ResponseStatusException.class, exception.getClass());
    assertEquals("Employee not found", exception.getReason());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  public void testUpdateEmployeeFailure() {
    when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
            .thenThrow(new RuntimeException("Update failed"));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      employeeController.updateEmployee(1L, emp1);
    });

    assertEquals(ResponseStatusException.class, exception.getClass());
    assertEquals("Failed to update employee", exception.getReason());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
  }
}
