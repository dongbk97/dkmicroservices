package dev.ngdangkiet.domain.tracking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class TrackingJson implements Serializable {

    private String action;
    private String requestBodyJson;
    private String responseBodyJson;
}
