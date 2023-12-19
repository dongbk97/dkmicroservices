package dev.ngdangkiet.config;

import com.netflix.discovery.EurekaClient;
import dev.ngdangkiet.dkmicroservices.employee.service.EmployeeServiceGrpc;
import dev.ngdangkiet.grpc.BaseGrpcServicesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author ngdangkiet
 * @since 11/18/2023
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
    public EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub() {
//        InstanceInfo instanceInfo = getGrpcInstanceInfo(ServiceConstant.EMPLOYEE_SERVICE);
//        return EmployeeServiceGrpc.newBlockingStub(newChannel(instanceInfo.getHostName(), instanceInfo.getPort()));
        return EmployeeServiceGrpc.newBlockingStub(newChannel(
                environment.getProperty("grpc.client.grpc-employee-service.host"),
                environment.getProperty("grpc.client.grpc-employee-service.port", Integer.class))
        );
    }
}
