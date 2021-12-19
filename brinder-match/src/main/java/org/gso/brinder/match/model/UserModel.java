package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private Double longitude;
    private Double latitude;

    public UserDto toDto() {
        return UserDto.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
