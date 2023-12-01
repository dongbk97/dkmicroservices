package dev.ngdangkiet.controller.recruitment;

import dev.ngdangkiet.client.RecruitmentGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsRequest;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.recruitment.applicant.ApplicantRequestMapper;
import dev.ngdangkiet.mapper.response.recruitment.applicant.ApplicantDetailResponseMapper;
import dev.ngdangkiet.mapper.response.recruitment.applicant.ApplicantListResponseMapper;
import dev.ngdangkiet.payload.request.recruitment.applicant.UpdateApplicantRequest;
import dev.ngdangkiet.payload.request.recruitment.applicant.UpsertApplicantRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
 * @since 11/29/2023
 */

@Tag(name = "Applicant", description = "Applicant APIs")
@RestController
@RequestMapping("/api/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final RecruitmentGrpcClient recruitmentGrpcClient;
    private final ApplicantRequestMapper applicantRequestMapper = ApplicantRequestMapper.INSTANCE;
    private final ApplicantListResponseMapper applicantListResponseMapper = ApplicantListResponseMapper.INSTANCE;
    private final ApplicantDetailResponseMapper applicantDetailResponseMapper = ApplicantDetailResponseMapper.INSTANCE;

    @PostMapping
    public ApiMessage createApplicant(@Valid @RequestBody UpsertApplicantRequest request) {
        try {
            var response = recruitmentGrpcClient.upsertApplicant(applicantRequestMapper.toProtobuf(request));
            return ErrorHelper.isSuccess((int) response)
                    ? ApiMessage.success(response)
                    : ApiMessage.failed((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateApplicant(@Valid @RequestBody UpdateApplicantRequest request) {
        try {
            var response = recruitmentGrpcClient.upsertApplicant(applicantRequestMapper.toProtobuf(request)
                    .toBuilder()
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
    public ApiMessage getApplicants(@RequestParam(name = "jobPostingId", required = false) Long jobPostingId,
                                    @RequestParam(name = "status", required = false) String status) {
        try {
            var response = recruitmentGrpcClient.getApplicants(PGetApplicantsRequest.newBuilder()
                    .setJosPostingId(ObjectUtils.defaultIfNull(jobPostingId, -1L))
                    .setStatus(StringUtils.defaultString(status))
                    .build());
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(applicantListResponseMapper.toDomains(response.getDataList()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{id}")
    public ApiMessage getApplicantById(@PathVariable(value = "id") Long applicantId) {
        try {
            var response = recruitmentGrpcClient.getApplicantById(applicantId);
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(applicantDetailResponseMapper.toDomain(response.getData()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deleteApplicantById(@PathVariable(value = "id") Long applicantId) {
        try {
            var response = recruitmentGrpcClient.deleteApplicantById(applicantId);
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.SUCCESS
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
