package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.JobPostingEntity;
import dev.ngdangkiet.enums.recruitment.JobPostingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingEntity, Long> {

    List<JobPostingEntity> findAllByStatus(JobPostingStatus status);
}
