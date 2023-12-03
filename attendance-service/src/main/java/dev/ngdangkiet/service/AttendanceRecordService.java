package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

public interface AttendanceRecordService {

    EmptyResponse checkInOut(Int64Value employeeId);
}
