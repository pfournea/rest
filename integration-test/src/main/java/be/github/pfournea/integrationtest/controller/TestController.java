package be.github.pfournea.integrationtest.controller;

import be.github.pfournea.rest.client.RestClient;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Created by Peter on 30/10/2016.
 */
@RestController
public class TestController {
    @Autowired
    private RestClient restClient;

    @Autowired
    @Qualifier("zuulRestClient")
    private RestClient zuulRestClient;

    @RequestMapping(value = "/api/test", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto getResponse() {
        return restClient.getById(UUID.randomUUID(),ResponseDto.class);
    }

    @RequestMapping(value = "/api/zuultest", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto getResponseByZuul() {
        return zuulRestClient.getById(UUID.randomUUID(),ResponseDto.class);
    }

    public static class ResponseDto {
        private String name;
        private String description;

        @JsonCreator
        public ResponseDto(@JsonProperty(value = "name") String name,
                           @JsonProperty(value = "description") String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}
