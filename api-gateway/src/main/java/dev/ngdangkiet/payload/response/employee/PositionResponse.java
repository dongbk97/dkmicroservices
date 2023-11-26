package dev.ngdangkiet.payload.response.employee;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

@Getter
@Setter
public class PositionResponse implements Serializable {

    private Long id;
    private String name;
}
