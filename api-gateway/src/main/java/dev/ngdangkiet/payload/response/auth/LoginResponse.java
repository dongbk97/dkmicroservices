package dev.ngdangkiet.payload.response.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;
    private String tokenUUID;
    private UserInfo userInfo;

    @Getter
    @Setter
    @Builder
    public static class UserInfo {

        private Long id;
        private String fullName;
        private String email;
    }
}
