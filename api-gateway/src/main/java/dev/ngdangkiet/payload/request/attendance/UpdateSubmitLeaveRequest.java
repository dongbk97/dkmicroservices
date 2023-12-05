package dev.ngdangkiet.payload.request.attendance;

import dev.ngdangkiet.validation.annotation.ValidationLeaveRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Getter
@Setter
public class UpdateSubmitLeaveRequest extends SubmitLeaveRequest {

    @NotNull(message = "Id shouldn't be null or empty!")
    private Long id;

    @ValidationLeaveRequestStatus
    private String status;
}
