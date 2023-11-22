package dev.ngdangkiet.payload.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/22/2023
 */

@Getter
@Setter
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class RefreshTokenResponse {

    private String token;
}
