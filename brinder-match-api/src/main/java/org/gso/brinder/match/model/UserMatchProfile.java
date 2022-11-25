package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMatchProfile {

    @Id
    private String idMatchProfile;
    private String firstName;
    private String lastName;
    private GeoLocation geoLocation;

    public UserMatchProfile toDto() {
        return UserMatchProfile.builder()
                .idMatchProfile(this.idMatchProfile)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .geoLocation(this.geoLocation)
                .build();
    }
}
