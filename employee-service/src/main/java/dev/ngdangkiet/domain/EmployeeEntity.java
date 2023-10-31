package dev.ngdangkiet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
@Entity
@Table(name = "tbl_employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private Long departmentId;

    @ManyToOne(targetEntity = PositionEntity.class)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private PositionEntity position;
}
