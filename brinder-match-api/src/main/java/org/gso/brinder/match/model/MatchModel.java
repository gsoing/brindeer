package org.gso.brinder.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//TODO import org.gso.brinder.profile.model.ProfileModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    private double latitude;
    private double longitude;
    private ProfileModel profile;
}
