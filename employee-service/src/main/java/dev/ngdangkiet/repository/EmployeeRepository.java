package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT e FROM EmployeeEntity e " +
            "WHERE (:departmentId = -1 OR e.departmentId = :departmentId) " +
            "AND (:positionId = -1 OR e.position.id = :positionId)")
    List<EmployeeEntity> findByDepartmentAndPosition(long departmentId, long positionId);
}
