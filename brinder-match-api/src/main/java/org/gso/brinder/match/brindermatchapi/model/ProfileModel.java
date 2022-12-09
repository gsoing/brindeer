package org.gso.brinder.match.brindermatchapi.model;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.brindermatchapi.dto.ProfileMatchDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ProfileModel {

    @Id
    private String id;
    @Email
    private String mail;
    private String firstName;
    private String lastName;
    private double longitude;
    private double latitude;

    public ProfileMatchDto toProfileDto() {
        return ProfileMatchDto.builder()
                .id(this.id)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }

}