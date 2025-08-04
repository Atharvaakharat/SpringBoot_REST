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

  private Employee validEmployee;
  private Employee updatedEmployee;

  /**
   * Sets up.
   */
  @BeforeEach
  public void setUp() {
    validEmployee = new Employee();
    validEmployee.setId(1L);
    validEmployee.setName("Alice");
    validEmployee.setPosition("Developer");
    validEmployee.setMobileNo("9876543210");
    validEmployee.setAddress("Pune");

    updatedEmployee = new Employee();
    updatedEmployee.setName("Bob");
    updatedEmployee.setPosition("Senior Developer");
    updatedEmployee.setMobileNo("1234567890");
    updatedEmployee.setAddress("Mumbai");
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

  /**
   * Test save employee name is null should throw exception.
   */
  @Test
  public void testSaveEmployee_NameIsNull_ShouldThrowException() {
    Employee employee = new Employee();
    employee.setName(null);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.saveEmployee(employee);
    });

    assertEquals("Employee name is required", exception.getMessage());
  }

  /**
   * Test save employee name is blank should throw exception.
   */
  @Test
  public void testSaveEmployee_NameIsBlank_ShouldThrowException() {
    Employee employee = new Employee();
    employee.setName("   ");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.saveEmployee(employee);
    });

    assertEquals("Employee name is required", exception.getMessage());
  }

  /**
   * Test update employee name is null should throw exception.
   */
  @Test
  public void testUpdateEmployee_NameIsNull_ShouldThrowException() {
    Employee updateEmp = new Employee();
    updateEmp.setId(1L);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(updateEmp));

    Employee update = new Employee();
    update.setName(null);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.updateEmployee(1L, update);
    });

    assertEquals("Employee name is required for update", exception.getMessage());
  }


  /**
   * Test update employee name is blank should throw exception.
   */
  @Test
  public void testUpdateEmployee_NameIsBlank_ShouldThrowException() {
    Employee updateEmp = new Employee();
    updateEmp.setId(1L);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(updateEmp));

    Employee update = new Employee();
    update.setName("   ");

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      employeeService.updateEmployee(1L, update);
    });

    assertEquals("Employee name is required for update", exception.getMessage());
  }

  /**
   * Test update employee name is set correctly.
   */
  @Test
  public void testUpdateEmployee_NameIsSetCorrectly() {
    Employee updateEmp = new Employee();
    updateEmp.setId(1L);
    updateEmp.setName("Old Name");

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(updateEmp));
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

  /**
   * Test update address and position by id.
   */
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
  @Test
  public void testUpdateAddressAndPosition_ReturnZero() {
    Long employeeId = 1L;
    String newAddress = "New Street, Pune";
    String newPosition = "Senior Developer";

    when(employeeRepository.updateEmployeeAddressAndPositionById(employeeId, newAddress, newPosition))
            .thenReturn(0);

    int result = employeeService.updateAddressAndPositionById(employeeId, newAddress, newPosition);

    assertEquals(0, result);
    verify(employeeRepository, times(1))
            .updateEmployeeAddressAndPositionById(employeeId, newAddress, newPosition);
  }



}
