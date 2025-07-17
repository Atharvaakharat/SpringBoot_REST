package org.example.service;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Employee service impl test.
 */
public class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  /**
   * Sets up.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test get all employees.
   */
  @Test
  public void testGetAllEmployees() {
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee(), new Employee()));
    List<Employee> employees = employeeService.getAllEmployees();
    assertEquals(2, employees.size());
  }

  /**
   * Test save employee.
   */
  @Test
  public void testSaveEmployee() {
    Employee employee = new Employee();
    employee.setName("Alice");
    when(employeeRepository.save(employee)).thenReturn(employee);
    Employee saved = employeeService.saveEmployee(employee);
    assertEquals("Alice", saved.getName());
  }

  /**
   * Test update employee not found.
   */
  @Test
  public void testUpdateEmployee_NotFound() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
    Exception exception = assertThrows(RuntimeException.class, () -> {
      employeeService.updateEmployee(1L, new Employee());
    });
    assertEquals("Employee not found", exception.getMessage());
  }
}
