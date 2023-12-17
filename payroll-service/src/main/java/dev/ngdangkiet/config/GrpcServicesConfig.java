package dev.ngdangkiet.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.ngdangkiet.constant.ServiceConstant;
import dev.ngdangkiet.dkmicroservices.attendance.service.AttendanceServiceGrpc;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.grpc.BaseGrpcServicesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfig {

    public GrpcServicesConfig(EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    @Bean
    public EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.EMPLOYEE_SERVICE);
        return EmployeeServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }

    @Bean
    public AttendanceServiceGrpc.AttendanceServiceBlockingStub attendanceServiceBlockingStub() {
        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.ATTENDANCE_SERVICE);
        return AttendanceServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
    }
}
