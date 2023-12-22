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
 * @since 11/6/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_position", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class PositionEntity extends BaseEntity {

    private String name;
}
