package dev.ngdangkiet.client;

import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.attendance.service.AttendanceServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Component
@RequiredArgsConstructor
public class AttendanceGrpcClient {

    @GrpcClient("grpc-attendance-service")
    private final AttendanceServiceGrpc.AttendanceServiceBlockingStub attendanceServiceBlockingStub;

    public PGetTotalWorkingDayInMonthResponse getTotalWorkingDayInMonthResponse(PGetTotalWorkingDayInMonthRequest request) {
        return attendanceServiceBlockingStub.getTotalWorkingDayInMonth(request);
    }
}
