package be.github.pfournea.rest;

import be.github.pfournea.rest.client.RestClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestApplication.class, RestApplicationTests.TestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class RestApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestClient restClient;

    @Value("${local.server.port}")
    private String port;

    @Test
    public void exampleTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/resource/{uuid}", String.class, UUID.fromString("7d19787f-722b-4908-bdd0-61aa3f02fa40"));
        assertThat(response.getBody()).contains("description");
        Response resp = restClient.getById(UUID.randomUUID(), Response.class, "localhost", port);
        assertThat(resp).isNotNull();
        assertThat(resp).hasNoNullFieldsOrProperties();
        assertThat(resp).hasFieldOrProperty("descr");
        assertThat(resp).hasFieldOrProperty("name");
    }

    @Test
    @Ignore
    public void test404() {
        assertThatThrownBy(() -> restClient.getById(UUID.randomUUID(), Response.class, "localhost", port))
                .isInstanceOf(HttpClientErrorException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND);
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        RestClient getRestClient(TestRestTemplate testRestTemplate) {
            RestClient restClient = new RestClient(testRestTemplate.getRestTemplate());
            return restClient;
        }
    }

    public static class Response {
        private String name;
        @JsonProperty("description")
        private String descr;

        public String getName() {
            return name;
        }

        public String getDescr() {
            return descr;
        }
    }


}
