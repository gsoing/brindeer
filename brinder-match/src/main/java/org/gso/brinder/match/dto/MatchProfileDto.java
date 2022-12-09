package org.gso.brinder.match.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.brinder.match.model.MatchProfileModel;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchProfileDto {

    @NotEmpty
    private String id;
    private Double longitude;
    private Double latitude;

    public MatchProfileModel toModel(JwtAuthenticationToken principal) {
        String userId = principal.getToken().getClaimAsString("sub");
        String firstName = principal.getToken().getClaimAsString("given_name");
        String lastName = principal.getToken().getClaimAsString("family_name");
        String mail = principal.getToken().getClaimAsString("email");
        return MatchProfileModel.builder()
                .id(this.id)
                .userId(userId)
                .mail(mail)
                .firstName(firstName)
                .lastName(lastName)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
