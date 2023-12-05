package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PLeaveRequest;
import dev.ngdangkiet.domain.LeaveRequestEntity;
import dev.ngdangkiet.enums.attendance.LeaveRequestStatus;
import dev.ngdangkiet.enums.attendance.LeaveType;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface LeaveRequestMapper extends ProtobufMapper<LeaveRequestEntity, PLeaveRequest> {

    LeaveRequestMapper INSTANCE = Mappers.getMapper(LeaveRequestMapper.class);

    @Named("mapStatus2Enum")
    static LeaveRequestStatus mapStatus2Enum(String status) {
        return StringUtils.hasText(status) ? LeaveRequestStatus.of(status) : LeaveRequestStatus.PENDING;
    }

    @Named("mapStatus2String")
    static String mapStatus2String(LeaveRequestStatus status) {
        return status.name();
    }

    @Named("mapLeaveType2Enum")
    static LeaveType mapLeaveType2Enum(String leaveType) {
        return LeaveType.of(leaveType);
    }

    @Named("mapLeaveType2String")
    static String mapLeaveType2String(LeaveType leaveType) {
        return leaveType.name();
    }

    @Named("mapStartDate2LocalDate")
    static LocalDate mapStartDate2LocalDate(String startDate) {
        return DateTimeUtil.convert2Localdate(startDate);
    }

    @Named("mapStartDate2String")
    static String mapStartDate2String(LocalDate startDate) {
        return Objects.nonNull(startDate) ? startDate.toString() : EMPTY;
    }

    @Named("mapEndDate2LocalDate")
    static LocalDate mapEndDate2LocalDate(String endDate) {
        return DateTimeUtil.convert2Localdate(endDate);
    }

    @Named("mapEndDate2String")
    static String mapEndDate2String(LocalDate endDate) {
        return Objects.nonNull(endDate) ? endDate.toString() : EMPTY;
    }

    @Override
    @Mapping(target = "leaveType", qualifiedByName = "mapLeaveType2Enum")
    @Mapping(target = "status", qualifiedByName = "mapStatus2Enum")
    @Mapping(target = "startDate", qualifiedByName = "mapStartDate2LocalDate")
    @Mapping(target = "endDate", qualifiedByName = "mapEndDate2LocalDate")
    LeaveRequestEntity toDomain(PLeaveRequest protobuf);

    @Override
    @Mapping(target = "leaveType", qualifiedByName = "mapLeaveType2String")
    @Mapping(target = "status", qualifiedByName = "mapStatus2String")
    @Mapping(target = "startDate", qualifiedByName = "mapStartDate2String")
    @Mapping(target = "endDate", qualifiedByName = "mapEndDate2String")
    PLeaveRequest toProtobuf(LeaveRequestEntity domain);
}
