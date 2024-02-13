package org.gso.brinder.match.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainUserData {

    private String userId;
    private String mail;
    private String firstName;
    private String lastName;

}
