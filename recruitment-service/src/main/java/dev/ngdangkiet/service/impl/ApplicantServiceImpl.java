package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicantResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsResponse;
import dev.ngdangkiet.domain.ApplicantEntity;
import dev.ngdangkiet.domain.JobPostingEntity;
import dev.ngdangkiet.enums.recruitment.ApplicantStatus;
import dev.ngdangkiet.enums.recruitment.JobPostingStatus;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.ApplicantMapper;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.repository.ApplicantRepository;
import dev.ngdangkiet.repository.JobPostingRepository;
import dev.ngdangkiet.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicantMapper applicantMapper = ApplicantMapper.INSTANCE;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    public Int64Value upsertApplicant(PApplicant request) {
        long response = ErrorCode.FAILED;

        try {
            JobPostingEntity jobPosting = jobPostingRepository.findById(request.getJobPosting().getId()).orElse(null);

            if (Objects.isNull(jobPosting)) {
                log.error("Job posting [{}] not found!", request.getJobPosting().getId());
                return Int64Value.of(ErrorCode.NOT_FOUND);
            } else if (!JobPostingStatus.OPEN.equals(jobPosting.getStatus())) {
                log.error("Job posting status [{}] invalid!", jobPosting.getStatus());
                return Int64Value.of(ErrorCode.INVALID_DATA);
            }

            ApplicantEntity applicant;
            LocalDate applicationDate = LocalDate.now();

            if (request.getId() > 0) {
                applicant = applicantRepository.findById(request.getId()).orElse(null);
                if (Objects.isNull(applicant)) {
                    log.error("Applicant [{}] not found!", request.getId());
                    return Int64Value.of(ErrorCode.NOT_FOUND);
                }
                applicationDate = applicant.getApplicationDate();
            }

            applicant = applicantMapper.toDomain(request);
            applicant.setJobPosting(jobPosting);
            applicant.setApplicationDate(applicationDate);

            response = applicantRepository.save(applicant).getId();

            // Send notification to employees of HR Department
            if (request.getId() <= 0) {
                rabbitMQProducer.sendNewApplicantNotification(applicant);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public PApplicantResponse getApplicantById(Int64Value request) {
        PApplicantResponse.Builder builder = PApplicantResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            ApplicantEntity applicant = applicantRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(applicant)) {
                log.error("Applicant [{}] not found!", request.getValue());
                builder.setCode(ErrorCode.NOT_FOUND);
            } else {
                builder.setCode(ErrorCode.SUCCESS).setData(applicantMapper.toProtobuf(applicant));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PGetApplicantsResponse getApplicants(PGetApplicantsRequest request) {
        PGetApplicantsResponse.Builder builder = PGetApplicantsResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            List<ApplicantEntity> applicants = applicantRepository.findByStatusAndJobPosting(
                    StringUtils.hasText(request.getStatus()) ? ApplicantStatus.of(request.getStatus()) : null,
                    request.getJosPostingId()
            );
            builder.setCode(ErrorCode.SUCCESS).addAllData(applicantMapper.toProtobufs(applicants));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse deleteApplicantById(Int64Value request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            ApplicantEntity applicant = applicantRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(applicant)) {
                log.error("Applicant [{}] not found!", request.getValue());
                builder.setCode(ErrorCode.NOT_FOUND);
            } else {
                applicantRepository.delete(applicant);
                builder.setCode(ErrorCode.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
