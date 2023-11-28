package dev.ngdangkiet.controller.recruitment;

import dev.ngdangkiet.client.RecruitmentGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetJobPostingsRequest;
import dev.ngdangkiet.enums.JobPostingStatus;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.recruitment.JobPostingRequestMapper;
import dev.ngdangkiet.mapper.response.recruitment.JobPostingResponseMapper;
import dev.ngdangkiet.payload.request.recruitment.UpdateJobPostingRequest;
import dev.ngdangkiet.payload.request.recruitment.UpsertJobPostingRequest;
import dev.ngdangkiet.validation.ValidationJobPostingStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */


@Tag(name = "Job Posting", description = "Job Posting APIs")
@RestController
@RequestMapping("/api/v1/job-postings")
@RequiredArgsConstructor
public class JobPostingController {

    private final RecruitmentGrpcClient recruitmentGrpcClient;
    private final JobPostingRequestMapper jobPostingRequestMapper = JobPostingRequestMapper.INSTANCE;
    private final JobPostingResponseMapper jobPostingResponseMapper = JobPostingResponseMapper.INSTANCE;

    @PostMapping
    public ApiMessage createJobPosting(@Valid @RequestBody UpsertJobPostingRequest request) {
        try {
            var response = recruitmentGrpcClient.upsertJobPosting(jobPostingRequestMapper.toProtobuf(request));
            return ErrorHelper.isSuccess((int) response)
                    ? ApiMessage.success(response)
                    : ApiMessage.failed((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateJobPosting(@Valid @RequestBody UpdateJobPostingRequest request) {
        try {
            var response = recruitmentGrpcClient.upsertJobPosting(jobPostingRequestMapper.toProtobuf(request).toBuilder()
                    .setId(request.getId())
                    .setStatus(request.getStatus())
                    .build());
            return ErrorHelper.isSuccess((int) response)
                    ? ApiMessage.success(response)
                    : ApiMessage.failed((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping
    public ApiMessage getJobPostings(@ValidationJobPostingStatus @RequestParam(name = "status", required = false) String status) {
        try {
            var response = recruitmentGrpcClient.getJobPostings(PGetJobPostingsRequest.newBuilder()
                    .setStatus(StringUtils.defaultString(status, JobPostingStatus.ALL.name()))
                    .build());
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(jobPostingResponseMapper.toDomains(response.getDataList()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{id}")
    public ApiMessage getJobPostingById(@PathVariable(value = "id") Long jobPostingId) {
        try {
            var response = recruitmentGrpcClient.getJobPostingById(jobPostingId);
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(jobPostingResponseMapper.toDomain(response.getData()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deleteJobPostingById(@PathVariable(value = "id") Long jobPostingId) {
        try {
            var response = recruitmentGrpcClient.deleteJobPostingById(jobPostingId);
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.SUCCESS
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
