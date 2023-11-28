package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetJobPostingsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingsResponse;
import dev.ngdangkiet.domain.JobPostingEntity;
import dev.ngdangkiet.enums.JobPostingStatus;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.JobPostingMapper;
import dev.ngdangkiet.repository.JobPostingRepository;
import dev.ngdangkiet.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingMapper jobPostingMapper = JobPostingMapper.INSTANCE;

    @Override
    public Int64Value upsertJobPosting(PJobPosting pJobPosting) {
        long response = ErrorCode.FAILED;

        try {
            JobPostingEntity jobPosting;
            LocalDate postedDate = LocalDate.now();

            if (pJobPosting.getId() > 0) {
                jobPosting = jobPostingRepository.findById(pJobPosting.getId()).orElse(null);
                if (Objects.isNull(jobPosting)) {
                    log.error("Job posting [{}] not found!", pJobPosting.getId());
                    return Int64Value.of(ErrorCode.NOT_FOUND);
                }
                postedDate = jobPosting.getPostedDate();
            }

            jobPosting = jobPostingMapper.toDomain(pJobPosting);
            jobPosting.setPostedDate(postedDate);

            response = jobPostingRepository.save(jobPosting).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public PJobPostingsResponse getJobPostings(PGetJobPostingsRequest request) {
        PJobPostingsResponse.Builder builder = PJobPostingsResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            List<JobPostingEntity> jobPostings = JobPostingStatus.ALL.equals(JobPostingStatus.of(request.getStatus()))
                    ? jobPostingRepository.findAll()
                    : jobPostingRepository.findAllByStatus(JobPostingStatus.of(request.getStatus()));
            builder.setCode(ErrorCode.SUCCESS).addAllData(jobPostingMapper.toProtobufs(jobPostings));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PJobPostingResponse getJobPostingById(Int64Value request) {
        PJobPostingResponse.Builder builder = PJobPostingResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            JobPostingEntity jobPosting = jobPostingRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(jobPosting)) {
                builder.setCode(ErrorCode.NOT_FOUND);
            } else {
                builder.setCode(ErrorCode.SUCCESS).setData(jobPostingMapper.toProtobuf(jobPosting));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse deleteJobPostingById(Int64Value request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            JobPostingEntity jobPosting = jobPostingRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(jobPosting)) {
                builder.setCode(ErrorCode.NOT_FOUND);
            } else {
                jobPostingRepository.delete(jobPosting);
                builder.setCode(ErrorCode.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
