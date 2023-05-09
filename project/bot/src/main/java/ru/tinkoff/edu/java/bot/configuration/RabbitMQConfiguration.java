package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.model.dto.LinkUpdate;

import java.util.HashMap;
import java.util.Map;

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
    public Queue queueDlq(@Qualifier("queueName") String queueName){
        return QueueBuilder.durable(queueName + ".dlq").build();
    }
    @Bean
    public DirectExchange directExchangeDlq(@Qualifier("exchangeName") String exchangeName){
        return new DirectExchange(exchangeName + ".dlq");
    }
    @Bean
    public Binding bindingDlq(@Qualifier("queueDlq") Queue queueDlq,
                              @Qualifier("directExchangeDlq") DirectExchange directExchangeDlq){
        return BindingBuilder.bind(queueDlq).to(directExchangeDlq).withQueueName();
    }

    @Bean
    public ClassMapper classMapper(){
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdate", LinkUpdate.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.service.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper){
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
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
