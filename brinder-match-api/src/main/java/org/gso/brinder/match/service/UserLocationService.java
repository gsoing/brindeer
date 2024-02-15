package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.repository.UserLocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.ProfileRepository;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final UserLocationRepository userLocationRepository;
    private final ProfileRepository profileRepository;

    public UserLocation updateLocation(String userId, double longitude, double latitude) {
        UserLocation userLocation = UserLocation.builder()
                .userId(userId)
                .location(new Point(longitude, latitude))
                .build();
        return userLocationRepository.save(userLocation);
    }

    public List<UserLocation> findMatchesNearby(double longitude, double latitude) {
        double distanceInMeters = 100;
        return userLocationRepository.findByLocationNear(longitude, latitude, distanceInMeters);
    }

    public boolean existsById(String userId) {
        return userLocationRepository.existsById(userId);
    }

    public Optional<UserLocation> findById(String userId) {
        return userLocationRepository.findById(userId);
    }
    public Page<ProfileModel> searchByMail(String mail, Pageable pageable) {
        return profileRepository.findByMail(mail, pageable);
    }
}
