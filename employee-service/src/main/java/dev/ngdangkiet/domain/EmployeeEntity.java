package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.employee.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class EmployeeEntity extends BaseEntity {

    private String fullName;

    private String email;

    private String password;

    private Long departmentId;

    private Integer age;

    private LocalDate birthDay;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(targetEntity = PositionEntity.class)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private PositionEntity position;
}
