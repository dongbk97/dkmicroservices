package dev.ngdangkiet.payload.request.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Getter
@Setter
public class LoginRequest implements Serializable {

    private String email;
    private String password;
}
