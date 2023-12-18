package dev.ngdangkiet.config;

import com.netflix.discovery.EurekaClient;
import dev.ngdangkiet.dkmicroservices.attendance.service.AttendanceServiceGrpc;
import dev.ngdangkiet.dkmicroservices.auth.service.AuthServiceGrpc;
import dev.ngdangkiet.dkmicroservices.department.service.DepartmentServiceGrpc;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.dkmicroservices.location.service.LocationServiceGrpc;
import dev.ngdangkiet.dkmicroservices.notification.service.NotificationServiceGrpc;
import dev.ngdangkiet.dkmicroservices.payroll.service.PayrollServiceGrpc;
import dev.ngdangkiet.dkmicroservices.recruitment.service.ApplicantServiceGrpc;
import dev.ngdangkiet.dkmicroservices.recruitment.service.JobPostingServiceGrpc;
import dev.ngdangkiet.dkmicroservices.tracking.service.TrackingServiceGrpc;
import dev.ngdangkiet.grpc.BaseGrpcServicesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@SuppressWarnings("ALL")
@Configuration
public class GrpcServicesConfig extends BaseGrpcServicesConfig {

    private final Environment environment;

    public GrpcServicesConfig(EurekaClient eurekaClient, Environment environment) {
        super(eurekaClient);
        this.environment = environment;
    }

    @Bean
    public DepartmentServiceGrpc.DepartmentServiceBlockingStub departmentServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.DEPARTMENT_SERVICE);
//        return DepartmentServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return DepartmentServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-department-service.host"),
                environment.getProperty("grpc.client.grpc-department-service.port", Integer.class))
        );
    }

    @Bean
    public EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.EMPLOYEE_SERVICE);
//        return EmployeeServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return EmployeeServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-employee-service.host"),
                environment.getProperty("grpc.client.grpc-employee-service.port", Integer.class))
        );
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.AUTH_SERVICE);
//        return AuthServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return AuthServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-auth-service.host"),
                environment.getProperty("grpc.client.grpc-auth-service.port", Integer.class))
        );
    }

    @Bean
    public LocationServiceGrpc.LocationServiceBlockingStub locationServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.LOCATION_SERVICE);
//        return LocationServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return LocationServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-location-service.host"),
                environment.getProperty("grpc.client.grpc-location-service.port", Integer.class))
        );
    }

    @Bean
    public NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.NOTIFICATION_SERVICE);
//        return NotificationServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return NotificationServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-notification-service.host"),
                environment.getProperty("grpc.client.grpc-notification-service.port", Integer.class))
        );
    }

    @Bean
    public TrackingServiceGrpc.TrackingServiceBlockingStub trackingServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.TRACKING_SERVICE);
//        return TrackingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return TrackingServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-tracking-service.host"),
                environment.getProperty("grpc.client.grpc-tracking-service.port", Integer.class))
        );
    }

    @Bean
    public JobPostingServiceGrpc.JobPostingServiceBlockingStub jobPostingServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.RECRUITMENT_SERVICE);
//        return JobPostingServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return JobPostingServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-recruitment-service.host"),
                environment.getProperty("grpc.client.grpc-recruitment-service.port", Integer.class))
        );
    }

    @Bean
    public ApplicantServiceGrpc.ApplicantServiceBlockingStub applicantServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.RECRUITMENT_SERVICE);
//        return ApplicantServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return ApplicantServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-recruitment-service.host"),
                environment.getProperty("grpc.client.grpc-recruitment-service.port", Integer.class))
        );
    }

    @Bean
    public AttendanceServiceGrpc.AttendanceServiceBlockingStub attendanceServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.ATTENDANCE_SERVICE);
//        return AttendanceServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return AttendanceServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-attendance-service.host"),
                environment.getProperty("grpc.client.grpc-attendance-service.port", Integer.class))
        );
    }

    @Bean
    public PayrollServiceGrpc.PayrollServiceBlockingStub payrollServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.PAYROLL_SERVICE);
//        return PayrollServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return PayrollServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-payroll-service.host"),
                environment.getProperty("grpc.client.grpc-payroll-service.port", Integer.class))
        );
    }
}
