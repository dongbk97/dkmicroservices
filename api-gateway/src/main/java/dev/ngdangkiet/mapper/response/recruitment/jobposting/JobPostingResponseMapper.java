package dev.ngdangkiet.mapper.response.recruitment.jobposting;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.recruitment.jobposting.JobPostingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface JobPostingResponseMapper extends ProtobufMapper<JobPostingResponse, PJobPosting> {

    JobPostingResponseMapper INSTANCE = Mappers.getMapper(JobPostingResponseMapper.class);
}
