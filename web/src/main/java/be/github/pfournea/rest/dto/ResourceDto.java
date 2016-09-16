package be.github.pfournea.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Peter on 10/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceDto {
    private final String name;
    private final String description;

    @JsonCreator
    public ResourceDto(@JsonProperty(value = "name") String name,
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
