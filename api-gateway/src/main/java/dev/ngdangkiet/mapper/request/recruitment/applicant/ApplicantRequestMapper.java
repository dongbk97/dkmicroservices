package dev.ngdangkiet.mapper.request.recruitment.applicant;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.recruitment.applicant.UpsertApplicantRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface ApplicantRequestMapper extends ProtobufMapper<UpsertApplicantRequest, PApplicant> {

    ApplicantRequestMapper INSTANCE = Mappers.getMapper(ApplicantRequestMapper.class);
}
