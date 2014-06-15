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
        UserRepository userRepository = context.getBean(UserRepository.class);
        PiouRepository piouRepository = context.getBean(PiouRepository.class);
        UserService userService = context.getBean(UserService.class);
        PiouService piouService = context.getBean(PiouService.class);

        User devoxxFr = new User("devoxxFr");
        userRepository.save(devoxxFr);

        User thebignet = new User("thebignet");
        thebignet.addFollowing(devoxxFr);
        userRepository.save(thebignet);

        piouRepository.save(new Piou(thebignet,"Ceci est mon premier piou"));
        piouRepository.save(new Piou(thebignet,"Ceci est mon deuxieme piou"));
        piouRepository.save(new Piou(devoxxFr,"Bienvenue à DevoxxFR"));

        Collection<Piou> pious = piouRepository.findByUser(thebignet);
        pious.stream().forEach(System.out::println);

        UserDto user = userService.getUserWithFollowing(thebignet.getId());

        user.getFollowing().stream().map(f -> f.getId()).forEach(System.out::println);

        piouService.getTimeline(thebignet.getId()).forEach(System.out::println);

        context.close();
    }

}