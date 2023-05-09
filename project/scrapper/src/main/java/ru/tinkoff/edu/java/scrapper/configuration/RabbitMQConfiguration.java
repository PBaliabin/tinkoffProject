package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue queue(@Qualifier("queueName") String queueName,
                       @Qualifier("exchangeName") String exchangeName){
        return QueueBuilder
                .durable(queueName)
                .withArgument("x-dead-letter-exchange", exchangeName + ".dlq")
                .withArgument("x-dead-letter-routing-key", queueName + ".dlq")
                .build();
    }
    @Bean
    public DirectExchange directExchange(@Qualifier("exchangeName") String exchangeName){
        return new DirectExchange(exchangeName);
    }
    @Bean
    public Binding binding(Queue queue,
                           DirectExchange directExchange,
                           @Qualifier("routingKey") String routingKey){
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public ApplicationRunner runner(ConnectionFactory connectionFactory) {
        return args -> connectionFactory.createConnection().close();
    }
}
