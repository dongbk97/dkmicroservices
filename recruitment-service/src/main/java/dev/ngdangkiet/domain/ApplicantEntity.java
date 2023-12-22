package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.recruitment.ApplicantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_applicant")
public class ApplicantEntity extends BaseEntity {

    @ManyToOne(targetEntity = JobPostingEntity.class)
    @JoinColumn(name = "job_posting_id", referencedColumnName = "id")
    private JobPostingEntity jobPosting;

    private String applicantName;

    private String email;

    private String phoneNumber;

    private String resumeUrl;

    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicantStatus status;
}
