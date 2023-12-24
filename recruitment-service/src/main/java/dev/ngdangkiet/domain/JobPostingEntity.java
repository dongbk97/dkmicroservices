package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.recruitment.JobPostingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@SuperBuilder
@Entity
@Table(name = "tbl_job_posting")
public class JobPostingEntity extends BaseEntity {

    private String jobTitle;

    private String jobDescription;

    private LocalDate postedDate;

    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    private JobPostingStatus status;
}
