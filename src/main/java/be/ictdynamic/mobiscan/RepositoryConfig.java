package be.ictdynamic.mobiscan;

import be.ictdynamic.mobiscan.domain.CarPoolParking;
import be.ictdynamic.mobiscan.domain.Company;
import be.ictdynamic.mobiscan.domain.MobiscanRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(MobiscanRequest.class, CarPoolParking.class, Company.class);
    }
}