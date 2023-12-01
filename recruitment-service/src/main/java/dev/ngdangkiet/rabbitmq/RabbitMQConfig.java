package dev.ngdangkiet.rabbitmq;

import dev.ngdangkiet.constant.RabbitMQConstant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(RabbitMQConstant.Notification.NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue newApplicantNotificationQueue() {
        return new Queue(RabbitMQConstant.Notification.NEW_APPLICANT_NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding newApplicantNotificationBinding() {
        return BindingBuilder.bind(newApplicantNotificationQueue())
                .to(notificationExchange())
                .with(RabbitMQConstant.Notification.NEW_APPLICANT_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
