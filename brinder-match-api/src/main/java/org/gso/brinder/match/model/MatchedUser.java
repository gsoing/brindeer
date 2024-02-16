package org.gso.brinder.match.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchedUser {

    public MatchedUser (String idMatchedUser, String firstName, String lastName ){
        this.idMatchedUser = idMatchedUser;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    private String idMatchedUser;
    private String firstName;
    private String lastName;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)// This annotation ensures the field is geospatially indexed
    private GeoCoordinates geoCoordinates;

    public MatchedUser toDto() {
        return MatchedUser.builder()
                .idMatchedUser(this.idMatchedUser)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .geoCoordinates(this.geoCoordinates)
                .build();
    }

    public GeoCoordinates getGeoCoordinates() {
        return geoCoordinates;
    }
}
