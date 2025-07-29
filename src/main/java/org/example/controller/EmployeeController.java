package org.example.controller;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Employee controller.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  @GetMapping("/all")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    try {
      List<Employee> employees = employeeService.getAllEmployees();
      if (null == employees) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database connection failed");
      }
      return ResponseEntity.ok(employees);
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database connection failed", e);
    }
  }

  /**
   * Create employee response entity.
   *
   * @param employee the employee
   * @return the response entity
   */
  @PostMapping("/create")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    try {
      Employee savedEmployee = employeeService.saveEmployee(employee);
      return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid employee data", e);
    }
  }

  /**
   * Update employee response entity.
   *
   * @param id       the id
   * @param employee the employee
   * @return the response entity
   */
  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    try {
      Employee updatedEmployee = employeeService.updateEmployee(id, employee);
      return ResponseEntity.ok(updatedEmployee);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update employee", e);
    }
  }

  /**
   * Update address and position response entity.
   *
   * @param id       the id
   * @param address  the address
   * @param position the position
   * @return the response entity
   */
  @PatchMapping("/{id}/update-fields")
  public ResponseEntity<String> updateAddressAndPosition(@PathVariable Long id,
                                                         @RequestParam String address,
                                                         @RequestParam String position) {
    int updated = employeeService.updateAddressAndPositionById(id, address, position);
    if (updated > 0) {
      return ResponseEntity.ok("Employee updated successfully");
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }
  }
}

