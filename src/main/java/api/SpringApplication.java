package api;

import api.model.Developer;
import api.repository.DeveloperRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static api.model.Role.ROLE_ADMIN;
import static api.model.Role.ROLE_USER;


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
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            Developer developer = new Developer();
            developer.setUsername("Misha");
            developer.setEmail("Nozjkoitop@mail.ru");
            developer.setPassword("test");
            developer.setRoles(List.of(ROLE_ADMIN));
            developer.setIssuer("GP");
            developer.setSubject("Task2");
            developerRepository.save(developer);
            Developer developer2 = new Developer();
            developer2.setUsername("Mikhail");
            developer2.setEmail("MikhailSvyatohorof@gmail.com");
            developer2.setPassword("test");
            developer2.setRoles(List.of(ROLE_USER));
            developer2.setIssuer("GP");
            developer2.setSubject("Task2");
            developerRepository.save(developer2);
        };
    }


}
