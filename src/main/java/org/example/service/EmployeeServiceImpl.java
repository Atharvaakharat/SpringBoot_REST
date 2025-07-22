package org.example.service;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> getAllEmployees() {
    logger.info("Fetching all employees from DB");
    return employeeRepository.findAll();
  }

  @Override
  public Employee saveEmployee(Employee employee) {
    if (employee.getName() == null || employee.getName().isBlank()) {
      logger.warn("Attempted to save employee with missing name");
      throw new IllegalArgumentException("Employee name is required");
    }
    logger.info("Saving employee: {}", employee.getName());
    return employeeRepository.save(employee);
  }

  @Override
  public Employee updateEmployee(Long id, Employee employee) {
    logger.info("Updating employee with ID: {}", id);
    Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> {
              logger.warn("Employee with ID {} not found", id);
              return new NoSuchElementException("Employee not found");
            });

    if (employee.getName() == null || employee.getName().isBlank()) {
      logger.warn("Invalid update data: name is missing");
      throw new IllegalArgumentException("Employee name is required for update");
    }

    existing.setName(employee.getName());
    existing.setPosition(employee.getPosition());
    existing.setMobileNo(employee.getMobileNo());
    existing.setAddress(employee.getAddress());

    return employeeRepository.save(existing);
  }
}
