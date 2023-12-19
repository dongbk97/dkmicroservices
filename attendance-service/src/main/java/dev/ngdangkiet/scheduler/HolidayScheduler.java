package dev.ngdangkiet.scheduler;

import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.domain.AttendanceRecordEntity;
import dev.ngdangkiet.domain.DTO.SearchHolidayDTO;
import dev.ngdangkiet.domain.HolidayEntity;
import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import dev.ngdangkiet.repository.AttendanceRecordRepository;
import dev.ngdangkiet.repository.HolidayRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import org.springframework.util.CollectionUtils;


import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayScheduler {
    private final EmployeeGrpcClient employeeGrpcClient;
    private final HolidayRepositoryCustom holidayRepositoryCustom;
    private final AttendanceRecordRepository attendanceRecordRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeAddHoliday() {
        log.info(" ============== executeAddHoliday task is running ============= ");
        //TODO: Auto add AttendanceRecordEntity if this Month have Holiday
        LocalDate now = LocalDate.now();
        try {
            // TODO: Get holidays
            SearchHolidayDTO searchHoliday = SearchHolidayDTO.builder()
                    .setYear(now.getYear())
                    .setMonth(now.getMonthValue()).build();
            List<HolidayEntity> holidays = holidayRepositoryCustom.findByConditions(searchHoliday);

            if (!CollectionUtils.isEmpty(holidays)) {

                // TODO: Get EmployeeIDs
                List<Long> employeeIds = employeeGrpcClient.getEmployees(PGetEmployeesRequest.newBuilder()
                                .setPositionId(-1L)
                                .setDepartmentId(-1L)
                                .build()).getDataList().stream()
                        .map(PEmployee::getId)
                        .toList();

                // TODO: set builder
                List<AttendanceRecordEntity> recordList = holidays.stream()
                        .flatMap(holiday -> employeeIds.stream()
                                .map(employeeId -> AttendanceRecordEntity.builder()
                                        .setEmployeeId(employeeId)
                                        .setAttendanceDate(holiday.getDate())
                                        .setStatus(AttendanceStatus.HOLIDAY)
                                        .build()))
                        .toList();

                if (!CollectionUtils.isEmpty(recordList)) {
                    attendanceRecordRepository.saveAll(recordList);
                }
            }
        } catch (Exception e) {
            log.error("Error while executing addHoliday: {}", e.getMessage());
        }
    }
}
