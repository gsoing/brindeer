package org.gso.brinder.match.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchedUser {

    @Id
    private String idMatchedUser;
    private String firstName;
    private String lastName;
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
