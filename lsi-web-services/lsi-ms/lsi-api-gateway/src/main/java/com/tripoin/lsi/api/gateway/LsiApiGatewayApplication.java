package com.tripoin.lsi.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created on Sep 20, 2018
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class LsiApiGatewayApplication {

    public static void main(String [] args) {
        SpringApplication.run(LsiApiGatewayApplication.class);
    }
}
