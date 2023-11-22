package dev.ngdangkiet;

import dev.ngdangkiet.dto.EmailDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServiceApplication {

//    @Autowired
//    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        System.out.println("Notification Service running!");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() throws Exception {
        Map<String, Object> properties = new HashMap<>();
//        emailService.sendMail("trong.le-duc@banvien.com.vn", "This Is Subject01", "this is body");

        EmailDTO details = new EmailDTO();
        details.setReceiverEmail("trong.le-duc@banvien.com.vn");
        details.setSubject("This Is Subject01");
        details.setMessage("<h1> hello Mr,${customerName} thank you very much!!!</h1>");
        properties.put("customerName", "Trong LE");
        details.setProperties(properties);
//        details.setAttachment("D:\\Trong.le-duc_Project\\Demo\\Sample-SpringBoot-Microservice\\Chat box.pdf");
//        emailService.sendMail(details, true);

    }
}