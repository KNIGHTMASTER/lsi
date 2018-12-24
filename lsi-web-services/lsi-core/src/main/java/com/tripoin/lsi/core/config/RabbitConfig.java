package com.tripoin.lsi.core.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 9/7/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue.file-upload}")
    private String queueFileUpload;

    @Value("${rabbitmq.fanout.exchange.file-upload}")
    private String fanoutExchangeFileUpload;

    @Bean
    public FanoutExchange fanoutExchangeFileUpload() {
        return new FanoutExchange(fanoutExchangeFileUpload);
    }

    @Bean
    public Queue queueFanoutFileUpload() {
        return new Queue(queueFileUpload);
    }

    @Bean
    public List<Declarable> fanoutBindingAPILogger() {
        return Arrays.asList(
                fanoutExchangeFileUpload(),
                queueFanoutFileUpload(),
                BindingBuilder.bind(queueFanoutFileUpload()).to(fanoutExchangeFileUpload())
        );
    }
}
