package org.gso.brinder.match.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Address {

    private String street;

    private String city;

    private String state;

    private int zipcode;

    private Location geoLocation;
}
