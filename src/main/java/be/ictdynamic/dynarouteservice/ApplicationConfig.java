package be.ictdynamic.dynarouteservice;

import be.ictdynamic.dynarouteservice.domain.Dummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Dummy dummy() {
        return new Dummy();
    }

}
