package be.github.pfournea.integrationtest.controller;

import be.github.pfournea.integrationtest.Application;
import be.github.pfournea.integrationtest.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Peter on 30/10/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, ApplicationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class TestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testController() {
        ResponseEntity<TestController.ResponseDto> response = restTemplate.getForEntity("/api/test", TestController.ResponseDto.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasNoNullFieldsOrProperties();
        assertThat(response.getBody()).hasFieldOrProperty("description");
        assertThat(response.getBody()).hasFieldOrProperty("name");
    }
}