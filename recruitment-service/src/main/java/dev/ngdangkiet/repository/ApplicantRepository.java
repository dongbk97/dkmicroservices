package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.ApplicantEntity;
import dev.ngdangkiet.enums.recruitment.ApplicantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {

    @Query("select a from ApplicantEntity a " +
            "join fetch a.jobPosting jp " +
            "where (:jobPostingId = -1 or jp.id = :jobPostingId) " +
            "and (:status is null or a.status = :status)")
    List<ApplicantEntity> findByStatusAndJobPosting(ApplicantStatus status, Long jobPostingId);
}
