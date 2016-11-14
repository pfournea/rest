package be.github.pfournea.integrationtest;

import be.github.pfournea.rest.client.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Peter on 30/10/2016.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("zuulResttemplate")
    public RestTemplate getZuulResttemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public RestClient getRestClient() {
        return new RestClient(getRestTemplate());
    }

    @Bean
    @Qualifier("zuulRestClient")
    public RestClient getRestClientWithZuul() {
        return new RestClient(getZuulResttemplate());
    }
}
