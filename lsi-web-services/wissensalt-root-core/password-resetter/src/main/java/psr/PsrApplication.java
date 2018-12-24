package psr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created on 12/1/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@ComponentScan({
        "com.wissensalt.scaffolding",
        "com.wissensalt.shared.entity",
        "psr"
})
@EnableJpaRepositories({
        "com.wissensalt.scaffolding",
        "psr",
})
@EntityScan({
        "com.wissensalt.shared.entity",
})
@SpringBootApplication
public class PsrApplication {

    @Autowired
    private PasswordUpdater passwordUpdater;

    public static void main(String [] args) {
        SpringApplication.run(PsrApplication.class);
    }

    @Bean
    public CommandLineRunner runner() {
        return s -> {
            System.out.println("Starting..");
            passwordUpdater.updatePassword();
//            passwordUpdater.matcher();
//            passwordUpdater.updateSingle();
        };
    }
}
