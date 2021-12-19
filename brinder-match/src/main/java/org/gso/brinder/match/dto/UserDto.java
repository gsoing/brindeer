package org.gso.brinder.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.model.UserModel;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    private String id;
    private String firstname;
    private String lastname;
    private Double longitude;
    private Double latitude;

    public UserModel toModel() {
        return UserModel.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
