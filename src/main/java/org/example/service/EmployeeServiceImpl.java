package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Employee service.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

  private final EmployeeRepository employeeRepository;

  /**
   * Instantiates a new Employee service.
   *
   * @param employeeRepository the employee repository
   */
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> getAllEmployees() {
    logger.info("Fetching all employees from the database");
    return employeeRepository.findAll();
  }

  @Override
  public Employee saveEmployee(Employee employee) {
    logger.info("Attempting to save employee: {}", employee.getName());
    if (employee.getName() == null || employee.getName().isBlank()) {
      logger.warn("Employee name is missing or blank");
      throw new IllegalArgumentException("Employee name is required");
    }
    Employee saved = employeeRepository.save(employee);
    logger.info("Employee saved successfully with ID: {}", saved.getId());
    return saved;
  }

  @Override
  public Employee updateEmployee(Long id, Employee employee) {
    logger.info("Updating employee with ID: {}", id);
    Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> {
              logger.error("Employee not found with ID: {}", id);
              return new NoSuchElementException("Employee not found");
            });

    if (employee.getName() == null || employee.getName().isBlank()) {
      logger.warn("Employee name is missing or blank during update");
      throw new IllegalArgumentException("Employee name is required for update");
    }

    existing.setName(employee.getName());
    existing.setPosition(employee.getPosition());
    existing.setMobileNo(employee.getMobileNo());
    existing.setAddress(employee.getAddress());

    Employee updated = employeeRepository.save(existing);
    logger.info("Employee updated successfully with ID: {}", updated.getId());
    return updated;
  }

  @Override
  public int updateAddressAndPositionById(Long id, String address, String position) {
    logger.info("Updating address and position for employee ID: {}", id);
    int updated = employeeRepository.updateEmployeeAddressAndPositionById(id, address, position);
    if (0 < updated) {
      logger.info("Employee address and position updated successfully");
    } else {
      logger.warn("No employee found with ID: {}", id);
    }
    return updated;
  }
}
