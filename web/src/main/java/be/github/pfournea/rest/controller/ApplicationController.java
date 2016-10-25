package be.github.pfournea.rest.controller;

import be.github.pfournea.rest.dto.ResourceDto;
import be.github.pfournea.rest.repository.ResourceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Peter on 10/09/2016.
 */
@RestController
public class ApplicationController {
    private ResourceRepository resourceRepository;

    public ApplicationController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @RequestMapping(value = "/api/resource/{resourceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResourceDto getResource(@PathVariable(value = "resourceId") UUID resourceId) {
        return resourceRepository.getResourceById(resourceId).get();
    }

    @RequestMapping(value = "/api/boereleute")
    public String getPipi() {
        return "boereleute";
    }

}
