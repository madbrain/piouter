package piouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import piouter.dto.UserDto;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;
import piouter.service.PiouService;
import piouter.service.UserService;

import java.util.Collection;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
    }

}