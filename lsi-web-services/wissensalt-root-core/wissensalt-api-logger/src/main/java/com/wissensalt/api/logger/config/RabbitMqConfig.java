package com.wissensalt.api.logger.config;

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
 * Created on 3/1/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.api-logger}")
    private String queueApiLogger;

    @Value("${rabbitmq.queue.jwt-logger}")
    private String queueJwtLogger;

    @Value("${rabbitmq.queue.auth-logger}")
    private String queueAuthLogger;

    @Value("${rabbitmq.fanout.exchange.api-logger}")
    private String fanoutExchangeAPILogger;

    @Value("${rabbitmq.fanout.exchange.jwt-logger}")
    private String fanoutExchangeJwtLogger;

    @Value("${rabbitmq.fanout.exchange.auth-logger}")
    private String fanoutExchangeAuthLogger;

    @Bean
    public FanoutExchange fanoutExchangeAPILogger() {
        return new FanoutExchange(fanoutExchangeAPILogger);
    }

    @Bean
    public FanoutExchange fanoutExchangeJwtLogger() {
        return new FanoutExchange(fanoutExchangeJwtLogger);
    }

    @Bean
    public FanoutExchange fanoutExchangeAuthLogger() {
        return new FanoutExchange(fanoutExchangeAuthLogger);
    }

    @Bean
    public Queue queueFanoutApiLogger() {
        return new Queue(queueApiLogger);
    }

    @Bean
    public Queue queueFanoutJwtLogger() {
        return new Queue(queueJwtLogger);
    }

    @Bean
    public Queue queueFanoutAuthLogger() {
        return new Queue(queueAuthLogger);
    }

    @Bean
    public List<Declarable> fanoutBindingAPILogger() {
        return Arrays.asList(
                fanoutExchangeAPILogger(),
                queueFanoutApiLogger(),
                BindingBuilder.bind(queueFanoutApiLogger()).to(fanoutExchangeAPILogger())
        );
    }

    @Bean
    public List<Declarable> fanoutBindingJwtLogger() {
        return Arrays.asList(
                fanoutExchangeJwtLogger(),
                queueFanoutJwtLogger(),
                BindingBuilder.bind(queueFanoutJwtLogger()).to(fanoutExchangeJwtLogger())
        );
    }

    @Bean
    public List<Declarable> fanoutBindingAuthLogger() {
        return Arrays.asList(
                fanoutExchangeAuthLogger(),
                queueFanoutAuthLogger(),
                BindingBuilder.bind(queueFanoutAuthLogger()).to(fanoutExchangeAuthLogger())
        );
    }
}
