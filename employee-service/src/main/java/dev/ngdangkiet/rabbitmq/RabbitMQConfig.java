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
 * @since 11/14/2023
 */

@Configuration
public class RabbitMQConfig {

    // QUEUE
    @Bean
    public Queue alertQueue() {
        return new Queue(RabbitMQConstant.Notification.ALERT_QUEUE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstant.Notification.EMAIL_QUEUE);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(RabbitMQConstant.Notification.SMS_QUEUE);
    }

    @Bean
    public Queue botQueue() {
        return new Queue(RabbitMQConstant.Notification.BOT_QUEUE);
    }
    // END QUEUE

    // EXCHANGE
    @Bean
    public TopicExchange alertExchange() {
        return new TopicExchange(RabbitMQConstant.Notification.ALERT_EXCHANGE);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(RabbitMQConstant.Notification.EMAIL_EXCHANGE);
    }

    @Bean
    public TopicExchange smsExchange() {
        return new TopicExchange(RabbitMQConstant.Notification.SMS_EXCHANGE);
    }

    @Bean
    public TopicExchange botExchange() {
        return new TopicExchange(RabbitMQConstant.Notification.BOT_EXCHANGE);
    }
    // END EXCHANGE

    // BINDING
    @Bean
    public Binding alertBinding() {
        return BindingBuilder.bind(alertQueue())
                .to(alertExchange())
                .with(RabbitMQConstant.Notification.ALERT_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(emailExchange())
                .with(RabbitMQConstant.Notification.EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue())
                .to(smsExchange())
                .with(RabbitMQConstant.Notification.SMS_ROUTING_KEY);
    }

    @Bean
    public Binding botBinding() {
        return BindingBuilder.bind(botQueue())
                .to(botExchange())
                .with(RabbitMQConstant.Notification.BOT_ROUTING_KEY);
    }
    // END BINDING

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
