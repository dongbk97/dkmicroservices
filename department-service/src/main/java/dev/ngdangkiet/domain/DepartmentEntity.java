package dev.ngdangkiet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@Table(name = "tbl_department", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class DepartmentEntity extends BaseEntity {

    private String name;
}
