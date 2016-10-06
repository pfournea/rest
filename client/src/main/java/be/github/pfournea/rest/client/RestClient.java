package be.github.pfournea.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Peter on 16/09/2016.
 */
public class RestClient<T> {
    @Value("${restapi.zuulurl}")
    private String zuulUrl;
    @Value("${restapi.eurekaurl}")
    private String eurekaUrl;

    @Value("${restapi.mediatype}")
    private String mediaType;

    private Class<T> classOfT;

    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T getById(UUID id,Class<T> type){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.valueOf(mediaType)));
        HttpEntity httpEntity = new HttpEntity(headers);
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(selectAppropriateUrl())
                .path("/api/resource/")
                .pathSegment("api", "resource", id.toString())
                .build();
        ResponseEntity<T> exchange = (ResponseEntity<T>) restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, httpEntity, classOfT);
        return exchange.getBody();
    }

    private String selectAppropriateUrl() {
        LoadBalanced[] declaredAnnotationsByType = restTemplate.getClass().getDeclaredAnnotationsByType(LoadBalanced.class);
        return declaredAnnotationsByType.length > 0 ? eurekaUrl : zuulUrl;
    }
}
