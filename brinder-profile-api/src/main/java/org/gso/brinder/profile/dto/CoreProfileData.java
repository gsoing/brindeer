package org.gso.brinder.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoreProfileData {


    private String userId;
    private String mail;
    private String firstName;
    private String lastName;
    
}
