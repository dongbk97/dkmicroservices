package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.InterviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
@Entity
@Table(name = "tbl_interview")
public class InterviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
