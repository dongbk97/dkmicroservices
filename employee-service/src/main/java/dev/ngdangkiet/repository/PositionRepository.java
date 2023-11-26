package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    @Query("select p from PositionEntity p where lower(p.name) = lower(:name)")
    Optional<PositionEntity> findByName(String name);
}
