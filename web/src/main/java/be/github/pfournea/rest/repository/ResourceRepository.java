package be.github.pfournea.rest.repository;

import be.github.pfournea.rest.dto.ResourceDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Peter on 10/09/2016.
 */
public interface ResourceRepository {
    Optional<ResourceDto> getResourceById(UUID id);
}
