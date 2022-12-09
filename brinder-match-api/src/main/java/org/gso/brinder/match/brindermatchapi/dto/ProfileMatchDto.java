package org.gso.brinder.match.brindermatchapi.dto;

import java.security.Principal;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.brindermatchapi.model.ProfileModel;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProfileMatchDto {

    private String id;
    private double longitude;
    private double latitude;

    public ProfileModel toModel(Principal principal) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) principal;
        return ProfileModel.builder()
                .id(this.id)
                .mail(jwtAuth.getToken().getClaimAsString("email"))
                .firstName(jwtAuth.getToken().getClaimAsString("given_name"))
                .lastName(jwtAuth.getToken().getClaimAsString("family_name"))
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}