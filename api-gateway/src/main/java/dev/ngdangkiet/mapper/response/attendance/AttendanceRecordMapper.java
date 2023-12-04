package dev.ngdangkiet.mapper.response.attendance;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PAttendanceRecord;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.attendance.AttendanceRecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface AttendanceRecordMapper extends ProtobufMapper<AttendanceRecordResponse, PAttendanceRecord> {

    AttendanceRecordMapper INSTANCE = Mappers.getMapper(AttendanceRecordMapper.class);
}
