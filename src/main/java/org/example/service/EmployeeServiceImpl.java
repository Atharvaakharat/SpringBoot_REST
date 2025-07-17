package org.example.service;

import java.util.List;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service implementation for managing Employee operations.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

  @Autowired
  private EmployeeRepository employeeRepository;

  /**
   * Retrieves all employees from the database.
   *
   * @return a list of all employees
   */
  @Override
  public List<Employee> getAllEmployees() {
    logger.info("Fetching all employees");
    return employeeRepository.findAll();
  }

  /**
   * Saves a new employee to the database.
   *
   * @param employee the employee to save
   * @return the saved employee
   */
  @Override
  public Employee saveEmployee(Employee employee) {
    logger.info("Saving employee: {}", employee.getName());
    return employeeRepository.save(employee);
  }

  /**
   * Updates an existing employee by ID with new details.
   *
   * @param id       the ID of the employee to update
   * @param employee the updated employee data
   * @return the updated employee
   * @throws RuntimeException if the employee is not found
   */
  @Override
  public Employee updateEmployee(Long id, Employee employee) {
    logger.info("Updating employee with id: {}", id);
    Employee existingEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    existingEmployee.setName(employee.getName());
    existingEmployee.setPosition(employee.getPosition());
    existingEmployee.setMobileNo(employee.getMobileNo());
    existingEmployee.setAddress(employee.getAddress());
    return employeeRepository.save(existingEmployee);
  }
}
