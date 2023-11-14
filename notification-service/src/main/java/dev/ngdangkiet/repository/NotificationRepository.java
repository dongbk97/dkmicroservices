package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
