package org.gso.brinder.match.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Location {
    private Float longitude;

    private Float latitude;

}
