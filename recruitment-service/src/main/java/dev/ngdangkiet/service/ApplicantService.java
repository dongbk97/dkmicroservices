package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicantResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsResponse;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

public interface ApplicantService {

    Int64Value upsertApplicant(PApplicant request);

    PApplicantResponse getApplicantById(Int64Value request);

    PGetApplicantsResponse getApplicants(PGetApplicantsRequest request);

    EmptyResponse deleteApplicantById(Int64Value request);
}
