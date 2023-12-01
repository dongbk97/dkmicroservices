package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.domain.ApplicantEntity;
import dev.ngdangkiet.enums.ApplicantStatus;
import dev.ngdangkiet.util.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface ApplicantMapper extends ProtobufMapper<ApplicantEntity, PApplicant> {

    ApplicantMapper INSTANCE = Mappers.getMapper(ApplicantMapper.class);

    @Named("mapApplicationDate2LocalDate")
    static LocalDate mapApplicationDate2LocalDate(String applicationDate) {
        return DateTimeUtil.convert2Localdate(applicationDate);
    }

    @Named("mapApplicationDate2String")
    static String mapApplicationDate2String(LocalDate applicationDate) {
        return Objects.nonNull(applicationDate) ? applicationDate.toString() : EMPTY;
    }

    @Named("mapStatus2Enum")
    static ApplicantStatus mapStatus2Enum(String status) {
        return StringUtils.hasText(status) ? ApplicantStatus.of(status) : ApplicantStatus.SUBMITTED/*create new applicant*/;
    }

    @Named("mapStatus2String")
    static String mapStatus2String(ApplicantStatus status) {
        return status.name();
    }

    @Override
    @Mapping(target = "applicationDate", qualifiedByName = "mapApplicationDate2LocalDate")
    @Mapping(target = "status", qualifiedByName = "mapStatus2Enum")
    @Mapping(source = "jobPosting", target = "jobPosting", ignore = true)
    ApplicantEntity toDomain(PApplicant protobuf);

    @Override
    @Mapping(target = "applicationDate", qualifiedByName = "mapApplicationDate2String")
    @Mapping(target = "status", qualifiedByName = "mapStatus2String")
    PApplicant toProtobuf(ApplicantEntity domain);
}
