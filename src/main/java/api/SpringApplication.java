package api;

import api.model.Developer;
import api.repository.DeveloperRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @Author : NozjkoiTop
 * @Date : Created in 12.02.2022
 */

@EnableJpaRepositories("api.repository")
@EntityScan("api.model")
@SpringBootApplication
@EnableSwagger2

public class SpringApplication {

    @Autowired
    private DeveloperRepository developerRepository;

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            Developer developer = new Developer();
            developer.setName("Misha");
            developer.setEmail("Nozjkoitop@mail.ru");
            developerRepository.save(developer);
            Developer developer2 = new Developer();
            developer2.setName("Mikhail");
            developer2.setEmail("MikhailSvyatohorof@gmail.com");
            developerRepository.save(developer2);
        };
    }


}
