package org.gso.brinder.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.profile.model.ProfileModel;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProfileModifiedDto {

    @Min(13)
    private int age;

    public ProfileModel toModel(String userId, String firstName, String lastName, String email) {
        return ProfileModel.builder()
                .id(userId)
                .userId(userId)
                .age(this.age)
                .firstName(firstName)
                .lastName(lastName)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .mail(email)
                .build();
    }
}
