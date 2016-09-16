package be.github.pfournea.rest.client;

import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Created by Peter on 16/09/2016.
 */
public class RestClient {

    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T getById(UUID id,Class<T> type){
        return null;
    }
}
