package dev.ngdangkiet.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.ngdangkiet.constant.ServiceConstant;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.grpc.BaseGrpcServicesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngdangkiet
 * @since 11/18/2023
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
}
