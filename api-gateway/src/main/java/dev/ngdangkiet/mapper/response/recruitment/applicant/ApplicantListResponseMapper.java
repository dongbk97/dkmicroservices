package dev.ngdangkiet.mapper.response.recruitment.applicant;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.recruitment.applicant.ApplicantListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface ApplicantListResponseMapper extends ProtobufMapper<ApplicantListResponse, PApplicant> {

    ApplicantListResponseMapper INSTANCE = Mappers.getMapper(ApplicantListResponseMapper.class);

    @Override
    @Mapping(source = "jobPosting.id", target = "jobPostingId")
    ApplicantListResponse toDomain(PApplicant protobuf);
}
