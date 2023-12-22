package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.recruitment.InterviewStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
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
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "tbl_interview")
public class InterviewEntity extends BaseEntity {

    @ManyToOne(targetEntity = ApplicantEntity.class)
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private ApplicantEntity applicant;

    @ElementCollection
    @CollectionTable(name = "interviewers", joinColumns = @JoinColumn(name = "interview_id"))
    private List<Long> interviewer_ids = new ArrayList<>();

    private LocalDate interviewDate;

    private String note;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
}
