package be.github.pfournea.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by Peter on 16/09/2016.
 */
public class RestClient {
    @Value("${restapi.zuulurl}")
    private String zuulUrl;
    @Value("${restapi.eurekaurl}")
    private String eurekaUrl;

    @Value("${restapi.mediatype}")
    private String mediaType;


    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T getById(UUID id,Class<T> classOfT){
        return getById(id, classOfT, () -> selectAppropriateUrl());
    }

    public <T> T getById(UUID id, Class<T> classOfT, String hostName, String port) {
        return getById(id, classOfT,() -> String.format("http://%s:%s/rest", hostName, port));
    }

    private <T> T getById(UUID id, Class<T> classOfT, Supplier<String> supplier) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.valueOf(mediaType)));
        HttpEntity httpEntity = new HttpEntity(headers);
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(supplier.get())
                .pathSegment("api", "resource", id.toString())
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, httpEntity, classOfT);
        return exchange.getBody();
    }

    private String selectAppropriateUrl() {
        LoadBalanced[] declaredAnnotationsByType = restTemplate.getClass().getDeclaredAnnotationsByType(LoadBalanced.class);
        return declaredAnnotationsByType.length > 0 ? eurekaUrl : zuulUrl;
    }
}
