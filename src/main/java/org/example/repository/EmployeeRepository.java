package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Employee repository.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  // ✅ Native query to update address and position by employee ID
  @Modifying
  @Transactional
  @Query(value = "UPDATE employee SET address = ?2, position = ?3 WHERE id = ?1", nativeQuery = true)
  int updateEmployeeAddressAndPositionById(Long id, String address, String position);
}
