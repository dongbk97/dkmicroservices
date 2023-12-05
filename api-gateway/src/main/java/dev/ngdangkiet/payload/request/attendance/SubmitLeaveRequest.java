package dev.ngdangkiet.payload.request.attendance;

import dev.ngdangkiet.validation.annotation.ValidationLeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Getter
@Setter
public class SubmitLeaveRequest implements Serializable {

    private Long employeeId;

    @ValidationLeaveType
    private String leaveType;

    @NotBlank(message = "Start date shouldn't be null or empty!")
    private String startDate;

    @NotBlank(message = "End date shouldn't be null or empty!")
    private String endDate;

    @NotBlank(message = "Reason shouldn't be null or empty!")
    private String reason;

    @NotNull(message = "Receiver ID shouldn't be null or empty!")
    private Long receiverId;
}
