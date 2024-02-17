package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.dto.MatchDto;
import org.gso.brinder.match.model.MatchModel;
import org.gso.brinder.match.model.ProfileModel;
//TODO import org.gso.brinder.profile.model.ProfileModel;
import org.gso.brinder.match.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public void updateUserLocation(String userId, double latitude, double longitude) {
        matchRepository.updateUserLocation(userId, latitude, longitude);
    }

    public List<ProfileModel> findNearbyUsers(String userId) {
        MatchDto user = matchRepository.findProfileLocationById(userId);
        double lat;
        double loong;
        return matchRepository.findNearbyUsers(lat, loong);
    }
}
