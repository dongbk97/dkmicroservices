package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.LeaveRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequestEntity, Long> {

    Optional<LeaveRequestEntity> findByIdAndEmployeeId(Long id, Long employeeId);

    Optional<LeaveRequestEntity> findByIdAndReceiverId(Long id, Long receiverId);
}
