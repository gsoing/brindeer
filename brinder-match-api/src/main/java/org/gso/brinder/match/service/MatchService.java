package org.gso.brinder.match.service;

import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {

    List<ProfileModel> getMatchesWithin100m(String profileId);
    String updateLocation (String profileId, Address address);

}