package org.example.controller;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Employee controller.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private EmployeeService employeeService;

  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  @GetMapping(
          value = "/all",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Employee>> getAllEmployees() {
    try {
      logger.info("Fetching all employees");
      return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error fetching employees", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching employees", e);
    }
  }

  /**
   * Create employee response entity.
   *
   * @param employee the employee
   * @return the response entity
   */
  @PostMapping(
          value = "/create",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    try {
      logger.info("Creating employee: {}", employee.getName());
      return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error("Error creating employee", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating employee", e);
    }
  }

  /**
   * Update employee response entity.
   *
   * @param id       the id
   * @param employee the employee
   * @return the response entity
   */
  @PutMapping(
          value = "/update/{id}",
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    try {
      logger.info("Updating employee with id: {}", id);
      return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    } catch (NoSuchElementException e) {
      logger.error("Employee not found", e);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found", e);
    } catch (Exception e) {
      logger.error("Error updating employee", e);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating employee", e);
    }
  }
}
