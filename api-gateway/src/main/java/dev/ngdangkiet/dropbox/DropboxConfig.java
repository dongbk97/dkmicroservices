package dev.ngdangkiet.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngdangkiet
 * @since 12/23/2023
 */

@Configuration
public class DropboxConfig {

    @Value("${security.dropbox.access-token}")
    private String accessToken;

    @Bean
    public DbxClientV2 dropboxClient() {
        return new DbxClientV2(DbxRequestConfig.newBuilder("ndk-microservices-storage").build(), accessToken);
    }
}
