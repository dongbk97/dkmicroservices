package dev.ngdangkiet.scheduler;

import dev.ngdangkiet.domain.JobPostingEntity;
import dev.ngdangkiet.enums.JobPostingStatus;
import dev.ngdangkiet.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTasks {

    private final JobPostingRepository jobPostingRepository;

    // Every day at 0:00 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void executeJobPosting() {
        List<JobPostingEntity> jobPostings = jobPostingRepository.findAllByStatus(JobPostingStatus.OPEN);
        List<JobPostingEntity> jobPostingsChangedStatus = new ArrayList<>();

        jobPostings.forEach(job -> {
            if (job.getApplicationDeadline().isBefore(LocalDate.now())) {
                job.setStatus(JobPostingStatus.CLOSED);
                jobPostingsChangedStatus.add(job);
            }
        });

        jobPostingRepository.saveAll(jobPostingsChangedStatus);
    }
}
