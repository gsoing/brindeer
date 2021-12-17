package org.gso.brinder.match.dto;

import lombok.Data;
import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;

@Data
public class ProfileDto {

    private String mail;
    private String name;


    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ProfileModel toProfileModel(){
        return ProfileModel.builder()
                .mail(this.mail)
                .name(this.name)
                .address(this.address)
                .build();
    }
}
