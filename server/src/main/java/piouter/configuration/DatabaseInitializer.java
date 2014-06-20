package piouter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PiouRepository piouRepository;

    @Bean
    public Integer databaseInitialization(){
        User devoxxFr = new User("info@devoxx.fr");
        userRepository.save(devoxxFr);

        User thebignet = new User("thebignet@gmail.com");
        thebignet.addFollowing(devoxxFr);
        userRepository.save(thebignet);

        piouRepository.save(new Piou(thebignet,"Ceci est mon premier piou"));
        piouRepository.save(new Piou(thebignet,"Ceci est mon deuxieme piou"));
        piouRepository.save(new Piou(devoxxFr,"Bienvenue à DevoxxFR"));
        return 0;
    }
}
