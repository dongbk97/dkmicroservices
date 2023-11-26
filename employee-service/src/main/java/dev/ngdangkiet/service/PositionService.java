package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionsResponse;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

public interface PositionService {

    Int64Value createOrUpdatePosition(PPosition position);

    PPositionResponse getPositionById(Int64Value positionId);

    PPositionsResponse getPositions();

    EmptyResponse deletePositionById(Int64Value positionId);
}
