package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
