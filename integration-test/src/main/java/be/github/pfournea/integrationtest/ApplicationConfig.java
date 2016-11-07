package be.github.pfournea.integrationtest;

import be.github.pfournea.rest.client.RestClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Peter on 30/10/2016.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestClient getRestClient() {
        return new RestClient(getRestTemplate());
    }
}
