package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.TrackingGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.mapper.response.UserTrackingActivityMapper;
import dev.ngdangkiet.payload.request.tracking.GetUserTrackingActivitiesRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Tag(name = "Tracking", description = "Tracking APIs")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TrackingController {

    private final TrackingGrpcClient trackingGrpcClient;
    private final UserTrackingActivityMapper userTrackingActivityMapper = UserTrackingActivityMapper.INSTANCE;

    @GetMapping("/user-tracking")
    public ApiMessage getUserTrackingActivities(@ModelAttribute GetUserTrackingActivitiesRequest userTrackingActivitiesRequest) {
        try {
            var response = trackingGrpcClient.getUserTrackingActivities(
                    PGetUserTrackingActivitiesRequest.newBuilder()
                            .setUserId(userTrackingActivitiesRequest.getUserId())
                            .setAction(StringUtils.defaultString(userTrackingActivitiesRequest.getAction()))
                            .setMethod(StringUtils.defaultString(userTrackingActivitiesRequest.getMethod()))
                            .build()
            );
            return ApiMessage.success(userTrackingActivityMapper.toDomains(response.getDataList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
