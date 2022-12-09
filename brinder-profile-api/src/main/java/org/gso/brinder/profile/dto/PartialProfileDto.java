package org.gso.brinder.profile.dto;

import java.security.Principal;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.profile.model.ProfileModel;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartialProfileDto {

    private String id;
    @Min(13)
    private int age;


    public ProfileModel toModel(JwtAuthenticationToken principal) {
        String userId = principal.getToken().getClaimAsString("sub");
        String firstName = principal.getToken().getClaimAsString("given_name");
        String lastName = principal.getToken().getClaimAsString("family_name");
        String mail = principal.getToken().getClaimAsString("email");
        return ProfileModel.builder()
                .id(this.id)
                .userId(userId)
                .age(this.age)
                .mail(mail)
                .firstName(firstName)
                .lastName(lastName)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();
    }
}
