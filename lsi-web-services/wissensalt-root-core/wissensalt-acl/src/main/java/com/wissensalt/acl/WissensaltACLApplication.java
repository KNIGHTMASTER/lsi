package com.wissensalt.acl;

import com.wissensalt.acl.config.JwtFilter;
import com.wissensalt.scaffolding.apilogger.LoggableDispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created on 5/30/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@SpringBootApplication
//@EnableEurekaClient
@ComponentScan({
        "com.wissensalt.scaffolding",
        "com.wissensalt.shared.entity",
        "com.wissensalt.acl"
})
@EnableJpaRepositories({
        "com.wissensalt.scaffolding",
        "com.wissensalt.acl",
})
@EntityScan({
        "com.wissensalt.shared.entity",
        "com.wissensalt.acl.model"
})
public class WissensaltACLApplication {

    @Bean
    @ConditionalOnProperty(name = "sso-jwt", havingValue = "true")
    public JwtFilter jwtFilter() {
        System.out.println("jwt filter created");
        return new JwtFilter();
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new LoggableDispatcherServlet();
    }

    public static void main(String [] args) {
        SpringApplication.run(WissensaltACLApplication.class, args);
    }
}
