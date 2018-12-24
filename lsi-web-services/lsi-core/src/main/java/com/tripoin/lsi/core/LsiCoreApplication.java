package com.tripoin.lsi.core;

import com.wissensalt.scaffolding.apilogger.LoggableDispatcherServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@SpringBootApplication
@ComponentScan(value = {
        "com.wissensalt.scaffolding",
        "com.tripoin.lsi.core",
        "com.wissensalt.shared.entity"
}, excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.wissensalt\\.scaffolding\\.integration\\..*")})
@EnableJpaRepositories({
        "com.wissensalt.scaffolding",
        "com.tripoin.lsi.core.dao"
})
@EntityScan({
        "com.wissensalt.shared.entity",
        "com.tripoin.lsi.core.data.model"
})
public class LsiCoreApplication {

    public static void main(String [] args) {
        SpringApplication.run(LsiCoreApplication.class);
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new LoggableDispatcherServlet();
    }
}
