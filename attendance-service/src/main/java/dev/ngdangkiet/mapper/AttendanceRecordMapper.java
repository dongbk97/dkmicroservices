package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PAttendanceRecord;
import dev.ngdangkiet.domain.AttendanceRecordEntity;
import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface AttendanceRecordMapper extends ProtobufMapper<AttendanceRecordEntity, PAttendanceRecord> {

    AttendanceRecordMapper INSTANCE = Mappers.getMapper(AttendanceRecordMapper.class);

    @Named("mapAttendance2LocalDate")
    static LocalDate mapAttendance2LocalDate(String attendanceDate) {
        return DateTimeUtil.convert2Localdate(attendanceDate);
    }

    @Named("mapAttendance2String")
    static String mapAttendance2String(LocalDate attendanceDate) {
        return Objects.nonNull(attendanceDate) ? attendanceDate.toString() : EMPTY;
    }

    @Named("mapCheckInTime2LocalTime")
    static LocalTime mapCheckInTime2LocalTime(String checkInTime) {
        return DateTimeUtil.convert2Localtime(checkInTime);
    }

    @Named("mapCheckInTime2String")
    static String mapCheckInTime2String(LocalTime checkInTime) {
        return Objects.nonNull(checkInTime) ? checkInTime.toString() : EMPTY;
    }

    @Named("mapCheckOutTime2LocalTime")
    static LocalTime mapCheckOutTime2LocalTime(String checkOutTime) {
        return DateTimeUtil.convert2Localtime(checkOutTime);
    }

    @Named("mapCheckOutTime2String")
    static String mapCheckOutTime2String(LocalTime checkOutTime) {
        return Objects.nonNull(checkOutTime) ? checkOutTime.toString() : EMPTY;
    }

    @Named("mapStatus2Enum")
    static AttendanceStatus mapStatus2Enum(String status) {
        return AttendanceStatus.of(status);
    }

    @Named("mapStatus2String")
    static String mapStatus2String(AttendanceStatus status) {
        return status.name();
    }

    @Override
    @Mapping(target = "attendanceDate", qualifiedByName = "mapAttendance2LocalDate")
    @Mapping(target = "checkInTime", qualifiedByName = "mapCheckInTime2LocalTime")
    @Mapping(target = "checkOutTime", qualifiedByName = "mapCheckOutTime2LocalTime")
    @Mapping(target = "status", qualifiedByName = "mapStatus2Enum")
    AttendanceRecordEntity toDomain(PAttendanceRecord protobuf);

    @Override
    @Mapping(target = "attendanceDate", qualifiedByName = "mapAttendance2String")
    @Mapping(target = "checkInTime", qualifiedByName = "mapCheckInTime2String")
    @Mapping(target = "checkOutTime", qualifiedByName = "mapCheckOutTime2String")
    @Mapping(target = "status", qualifiedByName = "mapStatus2String")
    PAttendanceRecord toProtobuf(AttendanceRecordEntity domain);
}
