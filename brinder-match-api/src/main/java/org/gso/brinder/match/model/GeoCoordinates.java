package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCoordinates {

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
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

