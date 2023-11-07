package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findEmployeeByEmail(String email);
}
