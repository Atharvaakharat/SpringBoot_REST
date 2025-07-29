package org.example.service;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Employee service impl test.
 */
@ExtendWith(MockitoExtension.class)
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
    // Custom setup logic can go here if needed
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
    employee.setPosition("Developer");
    employee.setMobileNo("9876543210");
    employee.setAddress("Pune");

    when(employeeRepository.save(employee)).thenReturn(employee);

    Employee saved = employeeService.saveEmployee(employee);

    // Verify all fields
    assertEquals("Alice", saved.getName());
    assertEquals("Developer", saved.getPosition());
    assertEquals("9876543210", saved.getMobileNo());
    assertEquals("Pune", saved.getAddress());

    // Verify repository interaction
    verify(employeeRepository, times(1)).save(employee);
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
  @Test
  public void testSaveEmployee_NameIsNull_ShouldThrowException() {
    Employee employee = new Employee();
    employee.setName(null);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.saveEmployee(employee);
    });

    assertEquals("Employee name is required", exception.getMessage());
  }

  @Test
  public void testSaveEmployee_NameIsBlank_ShouldThrowException() {
    Employee employee = new Employee();
    employee.setName("   ");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.saveEmployee(employee);
    });

    assertEquals("Employee name is required", exception.getMessage());
  }

  @Test
  public void testUpdateEmployee_NameIsNull_ShouldThrowException() {
    Employee existing = new Employee();
    existing.setId(1L);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing)); // <-- THIS IS CRITICAL

    Employee update = new Employee();
    update.setName(null);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.updateEmployee(1L, update); // <-- this hits line 48
    });

    assertEquals("Employee name is required for update", exception.getMessage());
  }


  @Test
  public void testUpdateEmployee_NameIsBlank_ShouldThrowException() {
    Employee existing = new Employee();
    existing.setId(1L);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));

    Employee update = new Employee();
    update.setName("   ");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.updateEmployee(1L, update);
    });

    assertEquals("Employee name is required for update", exception.getMessage());
  }

  @Test
  public void testUpdateEmployee_NameIsSetCorrectly() {
    Employee existing = new Employee();
    existing.setId(1L);
    existing.setName("Old Name");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
    when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Employee updated = new Employee();
    updated.setName("New Name");
    updated.setPosition("Dev");
    updated.setMobileNo("1234567890");
    updated.setAddress("Somewhere");

    Employee result = employeeService.updateEmployee(1L, updated);

    assertEquals("New Name", result.getName());
    assertEquals("Dev", result.getPosition());
    assertEquals("1234567890", result.getMobileNo());
    assertEquals("Somewhere", result.getAddress());
  }
  @Test
  public void testUpdateAddressAndPositionById() {
    Long employeeId = 1L;
    String newAddress = "New Street, Pune";
    String newPosition = "Senior Developer";

    when(employeeRepository.updateEmployeeAddressAndPositionById(employeeId, newAddress, newPosition))
            .thenReturn(1);

    int result = employeeService.updateAddressAndPositionById(employeeId, newAddress, newPosition);

    assertEquals(1, result);
    verify(employeeRepository, times(1))
            .updateEmployeeAddressAndPositionById(employeeId, newAddress, newPosition);
  }



}
