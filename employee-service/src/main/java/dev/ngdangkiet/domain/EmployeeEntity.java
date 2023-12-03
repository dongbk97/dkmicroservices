package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.employee.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private String password;

    private Long departmentId;

    private Integer age;

    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(targetEntity = PositionEntity.class)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private PositionEntity position;
}
