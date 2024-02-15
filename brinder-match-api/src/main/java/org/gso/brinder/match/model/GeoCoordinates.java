package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCoordinates {

    private GeoJsonPoint location; // Represents a GeoJSON point

    public GeoCoordinates(double latitude, double longitude) {
        this.location = new GeoJsonPoint(longitude, latitude); // Note the order: longitude, latitude
    }

    public double getLatitude() {
        return this.location.getY();
    }

    public double getLongitude() {
        return this.location.getX();
    }
}

