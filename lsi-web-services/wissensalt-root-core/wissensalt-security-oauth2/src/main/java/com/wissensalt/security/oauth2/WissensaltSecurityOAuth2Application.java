package com.wissensalt.security.oauth2;

import com.wissensalt.scaffolding.service.core.IUserService;
import com.wissensalt.security.oauth2.config.CustomJdbcTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;


/**
 * Created on 7/26/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@SpringBootApplication
//@EnableEurekaClient
@ComponentScan(value = {
        "com.wissensalt.scaffolding",
        "com.wissensalt.security.oauth2",
        "com.wissensalt.shared.entity"
}, excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.wissensalt\\.scaffolding\\.integration\\..*")})
@EnableJpaRepositories({
        "com.wissensalt.scaffolding",
        "com.wissensalt.security.oauth2"
})
@EntityScan({
        "com.wissensalt.shared.entity"
})
public class WissensaltSecurityOAuth2Application {

    @Autowired
    private IUserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new CustomJdbcTokenStore(dataSource);
    }

//        @Bean
    public CommandLineRunner command() {
        return s -> userService.encodePassword();
    }


    public static void main(String [] args) {
        SpringApplication.run(WissensaltSecurityOAuth2Application.class);
    }
}
