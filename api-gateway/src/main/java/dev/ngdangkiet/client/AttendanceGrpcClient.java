package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsResponse;
import dev.ngdangkiet.dkmicroservices.attendance.service.AttendanceServiceGrpc;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/3/2023
 */

@Component
@RequiredArgsConstructor
public class AttendanceGrpcClient {

    @GrpcClient("grpc-attendance-service")
    private final AttendanceServiceGrpc.AttendanceServiceBlockingStub attendanceServiceBlockingStub;

    public EmptyResponse checkInOut(Long employeeId) {
        return attendanceServiceBlockingStub.checkInOut(Int64Value.of(employeeId));
    }

    public PGetAttendanceRecordsResponse getAttendanceRecords(PGetAttendanceRecordsRequest request) {
        return attendanceServiceBlockingStub.getAttendanceRecords(request);
    }
}
