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
public class ProfileUpdateDto {

    private String id;
    @Min(13)
    private int age;

    public ProfileModel toCreateModel(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        return ProfileModel.builder()
                .id(this.id)
                .userId(jwtAuth.getToken().getClaimAsString("sub"))
                .mail(jwtAuth.getToken().getClaimAsString("email"))
                .firstName(jwtAuth.getToken().getClaimAsString("given_name"))
                .lastName(jwtAuth.getToken().getClaimAsString("family_name"))
                .age(this.age)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();
    }

    public ProfileModel toUpdateModel(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        return ProfileModel.builder()
                .id(this.id)
                .userId(jwtAuth.getToken().getClaimAsString("sub"))
                .mail(jwtAuth.getToken().getClaimAsString("email"))
                .firstName(jwtAuth.getToken().getClaimAsString("given_name"))
                .lastName(jwtAuth.getToken().getClaimAsString("family_name"))
                .age(this.age)
                .modified(LocalDateTime.now())
                .build();
    }
}
