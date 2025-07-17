package org.example.service;

import org.example.entity.Employee;

import java.util.List;

/**
 * The interface Employee service.
 */
public interface EmployeeService {
  /**
   * Gets all employees.
   *
   * @return the all employees
   */
  List<Employee> getAllEmployees();

  /**
   * Save employee employee.
   *
   * @param employee the employee
   * @return the employee
   */
  Employee saveEmployee(Employee employee);

  /**
   * Update employee employee.
   *
   * @param id       the id
   * @param employee the employee
   * @return the employee
   */
  Employee updateEmployee(Long id, Employee employee);
}
