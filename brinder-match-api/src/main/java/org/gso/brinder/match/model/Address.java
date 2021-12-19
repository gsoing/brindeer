package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private int streetNumber;

    private String streetName;

    private String cityName;

    private int zipCode;

    private Location location;
}
