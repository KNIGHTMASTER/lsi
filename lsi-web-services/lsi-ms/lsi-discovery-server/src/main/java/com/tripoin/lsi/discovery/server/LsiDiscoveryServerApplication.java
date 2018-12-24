package com.tripoin.lsi.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created on Sep 20, 2018
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@EnableEurekaServer
@SpringBootApplication
public class LsiDiscoveryServerApplication {
    public static void main(String [] args) {
        SpringApplication.run(LsiDiscoveryServerApplication.class);
    }
}
