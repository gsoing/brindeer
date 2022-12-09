package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.dto.MatchProfileDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class MatchProfileModel {

    @Id
    private String id;
    @NotEmpty
    private String userId;
    @Email
    private String mail;
    private String firstName;
    private String lastName;
    private String firstname;
    private String lastname;
    private Double longitude;
    private Double latitude;

    public MatchProfileDto toDto() {
        return MatchProfileDto.builder()
                .id(this.id)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
