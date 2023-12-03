package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.AttendanceGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.enums.tracking.Action;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.redis.utils.CacheTrackingUtil;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 12/3/2023
 */

@Tag(name = "Attendance", description = "Attendance APIs")
@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceGrpcClient attendanceGrpcClient;
    private final CacheTrackingUtil cacheTrackingUtil;

    @GetMapping("/check-in-out")
    public ApiMessage checkInOut() {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.checkInOut(userLogged.getUserInfo().getId());

            ApiMessage apiMessage = ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.FAILED;
            cacheTrackingUtil.cacheTrackingJson(Action.CHECK_IN_OUT.name(), userLogged.getUserInfo().getId(), apiMessage);

            return apiMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
