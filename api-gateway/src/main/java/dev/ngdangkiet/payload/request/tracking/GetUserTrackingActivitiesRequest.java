package dev.ngdangkiet.payload.request.tracking;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Getter
@Setter
public class GetUserTrackingActivitiesRequest implements Serializable {

    private Long userId;
    private String action;
    private String method;
}
