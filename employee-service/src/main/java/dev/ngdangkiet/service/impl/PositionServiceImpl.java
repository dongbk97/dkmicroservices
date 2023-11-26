package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPositionsResponse;
import dev.ngdangkiet.domain.PositionEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.PositionMapper;
import dev.ngdangkiet.repository.PositionRepository;
import dev.ngdangkiet.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper = PositionMapper.INSTANCE;

    @Override
    public Int64Value createOrUpdatePosition(PPosition pPosition) {
        long response = ErrorCode.FAILED;
        PositionEntity entity;

        if (pPosition.getId() > 0) {
            entity = positionRepository.findById(pPosition.getId()).orElse(null);
            if (Objects.isNull(entity)) {
                log.error("Position [{}] not found!", pPosition.getId());
                return Int64Value.of(ErrorCode.NOT_FOUND);
            }
        }

        if (existsPosition(pPosition)) {
            log.error("Position [{}] already exists!", pPosition.getName());
            return Int64Value.of(ErrorCode.ALREADY_EXISTS);
        }

        try {
            entity = positionMapper.toDomain(pPosition);
            response = positionRepository.save(entity).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    private boolean existsPosition(PPosition pPosition) {
        Optional<PositionEntity> entity = positionRepository.findByName(pPosition.getName());
        return entity.isPresent() && (pPosition.getId() <= 0 || pPosition.getId() != entity.get().getId());
    }

    @Override
    public PPositionResponse getPositionById(Int64Value positionId) {
        PPositionResponse.Builder builder = PPositionResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            PositionEntity entity = positionRepository.findById(positionId.getValue()).orElse(null);
            if (Objects.isNull(entity)) {
                log.error("Position [{}] not found!", positionId.getValue());
                builder.setCode(ErrorCode.NOT_FOUND);
            } else {
                builder.setCode(ErrorCode.SUCCESS).setData(positionMapper.toProtobuf(entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PPositionsResponse getPositions() {
        PPositionsResponse.Builder builder = PPositionsResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            List<PositionEntity> entities = positionRepository.findAll();
            builder.setCode(ErrorCode.SUCCESS).addAllData(positionMapper.toProtobufs(entities));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse deletePositionById(Int64Value positionId) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            PositionEntity entity = positionRepository.findById(positionId.getValue()).orElse(null);
            if (Objects.nonNull(entity)) {
                positionRepository.delete(entity);
                builder.setCode(ErrorCode.SUCCESS);
            } else {
                log.error("Position [{}] not found!", positionId.getValue());
                builder.setCode(ErrorCode.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}
