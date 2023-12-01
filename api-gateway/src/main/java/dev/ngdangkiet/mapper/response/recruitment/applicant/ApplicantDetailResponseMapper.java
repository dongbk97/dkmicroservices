package dev.ngdangkiet.mapper.response.recruitment.applicant;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.recruitment.applicant.ApplicantDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface ApplicantDetailResponseMapper extends ProtobufMapper<ApplicantDetailResponse, PApplicant> {

    ApplicantDetailResponseMapper INSTANCE = Mappers.getMapper(ApplicantDetailResponseMapper.class);
}
