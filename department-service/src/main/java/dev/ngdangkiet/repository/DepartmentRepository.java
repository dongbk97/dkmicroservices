package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query("select d from DepartmentEntity d where lower(d.name) = lower(:name)")
    Optional<DepartmentEntity> findByName(String name);
}
