package dev.ngdangkiet.mapper;

import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.domain.JobPostingEntity;
import dev.ngdangkiet.enums.JobPostingStatus;
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
 * @since 11/27/2023
 */

@Mapper(config = ProtobufMapperConfig.class)
public interface JobPostingMapper extends ProtobufMapper<JobPostingEntity, PJobPosting> {

    JobPostingMapper INSTANCE = Mappers.getMapper(JobPostingMapper.class);

    @Named("mapApplicationDeadline2LocalDate")
    static LocalDate mapApplicationDeadline2LocalDate(String applicationDeadline) {
        return DateTimeUtil.convert2Localdate(applicationDeadline);
    }

    @Named("mapPostedDate2LocalDate")
    static LocalDate mapPostedDate2LocalDate(String postedDate) {
        return DateTimeUtil.convert2Localdate(postedDate);
    }

    @Named("mapApplicationDeadline2String")
    static String mapApplicationDeadline2String(LocalDate applicationDeadline) {
        return Objects.nonNull(applicationDeadline) ? applicationDeadline.toString() : EMPTY;
    }

    @Named("mapPostedDate2String")
    static String mapPostedDate2String(LocalDate postedDate) {
        return Objects.nonNull(postedDate) ? postedDate.toString() : EMPTY;
    }

    @Named("mapStatus2Enum")
    static JobPostingStatus mapStatus2Enum(String status) {
        return StringUtils.hasText(status) ? JobPostingStatus.of(status) : JobPostingStatus.OPEN/*create new job posting*/;
    }

    @Named("mapStatus2String")
    static String mapStatus2String(JobPostingStatus status) {
        return status.name();
    }

    @Override
    @Mapping(target = "postedDate", qualifiedByName = "mapPostedDate2LocalDate")
    @Mapping(target = "applicationDeadline", qualifiedByName = "mapApplicationDeadline2LocalDate")
    @Mapping(target = "status", qualifiedByName = "mapStatus2Enum")
    JobPostingEntity toDomain(PJobPosting protobuf);

    @Override
    @Mapping(target = "postedDate", qualifiedByName = "mapPostedDate2String")
    @Mapping(target = "applicationDeadline", qualifiedByName = "mapApplicationDeadline2String")
    @Mapping(target = "status", qualifiedByName = "mapStatus2String")
    PJobPosting toProtobuf(JobPostingEntity domain);
}
