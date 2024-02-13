package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCoordinates {

    private double latitude;
    private double longitude;
}