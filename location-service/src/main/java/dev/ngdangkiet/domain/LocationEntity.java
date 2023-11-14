package dev.ngdangkiet.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author TrongLD
 * @since 11/09/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
@Entity
@Table(name = "tbl_location")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String address;
    private String latitude;
    private String longitude;
    private String fromAdress;
    private String toAddress;
}
