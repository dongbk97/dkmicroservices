package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    boolean existsByEmail(String email);
}
