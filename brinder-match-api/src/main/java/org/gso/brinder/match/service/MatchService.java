package org.gso.brinder.match.service;

import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.CustomMatchRepository;
import org.gso.brinder.match.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Distance;
import java.util.Optional;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final CustomMatchRepository customMatchRepository;

    public ProfileModel updateProfileLocation(ProfileModel profileToUpdate) {
        ProfileModel profileModel = this.getProfile(profileToUpdate.getId());
        profileModel.setLocation(profileToUpdate.getLocation());
        return matchRepository.save(profileModel);
    }

    public ProfileModel getProfile(String profileId) {
        return matchRepository.findById(profileId).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public Optional<ProfileModel> findByEmail(String email) {
        return matchRepository.findByMail(email);
    }

    public List<ProfileModel> findProfilesAround100m(GeoJsonPoint location) {
        return matchRepository.findProfilesNearLocation(location.getX(), location.getY(),
                new Distance(0.1, Metrics.KILOMETERS));
    }
}
