package org.example.service;

import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Employee service.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee saveEmployee(Employee employee) {
    if (employee.getName() == null || employee.getName().isBlank()) {
      throw new IllegalArgumentException("Employee name is required");
    }
    return employeeRepository.save(employee);
  }

  @Override
  public Employee updateEmployee(Long id, Employee employee) {
    Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Employee not found"));

    if (employee.getName() == null || employee.getName().isBlank()) {
      throw new IllegalArgumentException("Employee name is required for update");
    }

    existing.setName(employee.getName());
    existing.setPosition(employee.getPosition());
    existing.setMobileNo(employee.getMobileNo());
    existing.setAddress(employee.getAddress());

    return employeeRepository.save(existing);
  }

  @Override
  public int updateAddressAndPositionById(Long id, String address, String position) {
    return employeeRepository.updateEmployeeAddressAndPositionById(id, address, position);
  }
}
