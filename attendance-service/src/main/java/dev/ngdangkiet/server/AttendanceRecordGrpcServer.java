package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PChangeStatusLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PListHolidays;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysResponse;
import dev.ngdangkiet.dkmicroservices.attendance.service.AttendanceServiceGrpc;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.service.AttendanceRecordService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@GrpcService
@RequiredArgsConstructor
public class AttendanceRecordGrpcServer extends AttendanceServiceGrpc.AttendanceServiceImplBase {

    private final AttendanceRecordService attendanceRecordService;

    @Override
    public void checkInOut(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = attendanceRecordService.checkInOut(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAttendanceRecords(PGetAttendanceRecordsRequest request, StreamObserver<PGetAttendanceRecordsResponse> responseObserver) {
        PGetAttendanceRecordsResponse response = attendanceRecordService.getAttendanceRecords(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTotalWorkingDayInMonth(PGetTotalWorkingDayInMonthRequest request, StreamObserver<PGetTotalWorkingDayInMonthResponse> responseObserver) {
        PGetTotalWorkingDayInMonthResponse response = attendanceRecordService.getTotalWorkingDayInMonth(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void submitLeaveRequest(PLeaveRequest request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = attendanceRecordService.submitLeaveRequest(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void changeStatusLeaveRequest(PChangeStatusLeaveRequest request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = attendanceRecordService.changeStatusLeaveRequest(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void submitHolidays(PListHolidays requestList, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = attendanceRecordService.submitHolidays(requestList);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getHolidaysByConditions(PSearchHolidaysRequest request, StreamObserver<PSearchHolidaysResponse> responseObserver) {
        PSearchHolidaysResponse response = attendanceRecordService.getHolidaysByConditions(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
