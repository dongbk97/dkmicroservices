package dev.ngdangkiet.mapper.request.attendance;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PLeaveRequest;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.request.attendance.SubmitLeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LeaveRequestMapper extends ProtobufMapper<SubmitLeaveRequest, PLeaveRequest> {

    LeaveRequestMapper INSTANCE = Mappers.getMapper(LeaveRequestMapper.class);
}
