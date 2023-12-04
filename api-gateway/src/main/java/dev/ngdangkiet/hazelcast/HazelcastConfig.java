package dev.ngdangkiet.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Configuration
public class HazelcastConfig {

    @Value("#{'${hazelcast.dkmicroservices.address}'.split(',')}")
    protected String[] address;

    @Value("${hazelcast.dkmicroservices.cluster_name}")
    private String clusterName;

    @Bean(name = "hazelcastClient")
    public HazelcastInstance hazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().addAddress(address);
        clientConfig.getNetworkConfig().setConnectionTimeout(50000);
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}
