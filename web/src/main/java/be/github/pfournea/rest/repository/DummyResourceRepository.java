package be.github.pfournea.rest.repository;

import be.github.pfournea.rest.dto.ResourceDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Peter on 10/09/2016.
 */
@Repository
public class DummyResourceRepository implements ResourceRepository {
    @Override
    public Optional<ResourceDto> getResourceById(UUID id) {
        return Optional.of(new ResourceDto(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(10)));
    }
}
