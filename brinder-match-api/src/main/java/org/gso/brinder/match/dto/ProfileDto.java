package org.gso.brinder.match.dto;

import lombok.Builder;
import lombok.Data;
import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;

@Data
@Builder
public class ProfileDto {

    private String mail;
    private String name;
    private Address address;
    public ProfileModel toProfileModel(){
        return ProfileModel.builder()
                .mail(this.mail)
                .name(this.name)
                .address(this.address)
                .build();
    }
}
