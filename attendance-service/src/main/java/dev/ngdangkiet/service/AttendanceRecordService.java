package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PChangeStatusLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PLeaveRequest;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public interface AttendanceRecordService {

    EmptyResponse checkInOut(Int64Value employeeId);

    PGetAttendanceRecordsResponse getAttendanceRecords(PGetAttendanceRecordsRequest request);

    PGetTotalWorkingDayInMonthResponse getTotalWorkingDayInMonth(PGetTotalWorkingDayInMonthRequest request);

    EmptyResponse submitLeaveRequest(PLeaveRequest request);

    EmptyResponse changeStatusLeaveRequest(PChangeStatusLeaveRequest request);
}
