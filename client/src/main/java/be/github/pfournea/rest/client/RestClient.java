package be.github.pfournea.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.http.*;
import org.springframework.util.ClassUtils;
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
    @Value("${restapi.zuulurl:#{null}}")
    private String zuulUrl;
    @Value("${restapi.eurekaurl:#{null}}")
    private String eurekaUrl;

    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T getById(UUID id, Class<T> classOfT) {
        return getById(id, classOfT, () -> selectAppropriateUrl(), MediaType.APPLICATION_JSON);
    }

    public <T> T getById(UUID id, Class<T> classOfT, MediaType mediaType) {
        return getById(id, classOfT, () -> selectAppropriateUrl(), mediaType);
    }

    public <T> T getById(UUID id, Class<T> classOfT, String hostName, String port) {
        return getById(id, classOfT, () -> String.format("http://%s:%s/rest", hostName, port), MediaType.APPLICATION_JSON);
    }

    public <T> T getById(UUID id, Class<T> classOfT, String hostName, String port, MediaType mediaType) {
        return getById(id, classOfT, () -> String.format("http://%s:%s/rest", hostName, port), mediaType);
    }

    private <T> T getById(UUID id, Class<T> classOfT, Supplier<String> supplier, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(mediaType));
        HttpEntity httpEntity = new HttpEntity(headers);
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(supplier.get())
                .pathSegment("api", "resource", id.toString())
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, httpEntity, classOfT);
        return exchange.getBody();
    }

    private String selectAppropriateUrl() {
        if (ClassUtils.isPresent("org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor", ClassUtils.getDefaultClassLoader())) {
            boolean loadbalancerInterceptorFound = restTemplate.getInterceptors().stream().filter(clientHttpRequestInterceptor -> {
                try {
                    return Class.forName("org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor").isInstance(clientHttpRequestInterceptor);
                } catch (ClassNotFoundException e) {
                    return false;
                }
            }).findAny().isPresent();
            return loadbalancerInterceptorFound ? eurekaUrl : zuulUrl;
        } else {
            return zuulUrl;
        }
    }
}
