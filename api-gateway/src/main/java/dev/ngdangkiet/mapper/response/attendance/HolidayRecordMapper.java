package dev.ngdangkiet.mapper.response.attendance;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PHolidayRecord;
import dev.ngdangkiet.mapper.ProtobufMapper;
import dev.ngdangkiet.mapper.ProtobufMapperConfig;
import dev.ngdangkiet.payload.response.attendance.HolidayRecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = ProtobufMapperConfig.class)
public interface HolidayRecordMapper extends ProtobufMapper<HolidayRecordResponse, PHolidayRecord> {

    HolidayRecordMapper INSTANCE = Mappers.getMapper(HolidayRecordMapper.class);

}
