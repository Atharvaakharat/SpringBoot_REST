package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Employee repository.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
