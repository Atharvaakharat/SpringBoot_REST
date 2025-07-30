package org.example.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private static final Logger logger = LogManager.getLogger(EmployeeController.class);

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    logger.info("Received request to fetch all employees");
    try {
      List<Employee> employees = employeeService.getAllEmployees();
      if (employees == null) {
        logger.error("Employee list is null");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database connection failed");
      }
      return ResponseEntity.ok(employees);
    } catch (RuntimeException e) {
      logger.error("Error while fetching employees", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database connection failed", e);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    logger.info("Received request to create employee: {}", employee.getName());
    try {
      Employee savedEmployee = employeeService.saveEmployee(employee);
      return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      logger.error("Error while creating employee", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid employee data", e);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    logger.info("Received request to update employee with ID: {}", id);
    try {
      Employee updatedEmployee = employeeService.updateEmployee(id, employee);
      return ResponseEntity.ok(updatedEmployee);
    } catch (NoSuchElementException e) {
      logger.warn("Employee not found with ID: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
    } catch (RuntimeException e) {
      logger.error("Error while updating employee", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update employee", e);
    }
  }

  @PatchMapping("/{id}/update-fields")
  public ResponseEntity<String> updateAddressAndPosition(@PathVariable Long id,
                                                         @RequestParam String address,
                                                         @RequestParam String position) {
    logger.info("Received request to update address and position for employee ID: {}", id);
    int updated = employeeService.updateAddressAndPositionById(id, address, position);
    if (updated > 0) {
      return ResponseEntity.ok("Employee updated successfully");
    } else {
      logger.warn("Employee not found for ID: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
    }
  }
}
