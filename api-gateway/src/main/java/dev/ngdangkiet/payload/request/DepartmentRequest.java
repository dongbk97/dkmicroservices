package dev.ngdangkiet.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class DepartmentRequest implements Serializable {

    private Long id;
    private String name;
    private List<Long> employeeIds;
}
