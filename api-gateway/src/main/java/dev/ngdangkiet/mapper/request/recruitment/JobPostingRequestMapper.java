package dev.ngdangkiet.mapper.request.recruitment;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.recruitment.UpsertJobPostingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface JobPostingRequestMapper extends ProtobufMapper<UpsertJobPostingRequest, PJobPosting> {

    JobPostingRequestMapper INSTANCE = Mappers.getMapper(JobPostingRequestMapper.class);
}
