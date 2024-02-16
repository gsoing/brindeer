package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Location;
import org.gso.brinder.match.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.ProfileRepository;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ProfileRepository profileRepository;

    public Location updateLocation(String userId, double longitude, double latitude) {
        Location location = Location.builder()
                .userId(userId)
                .location(new Point(longitude, latitude))
                .build();
        return locationRepository.save(location);
    }

    public List<Location> findMatchesNearby(double longitude, double latitude) {
        double distanceInMeters = 100;
        return locationRepository.findByLocationNear(longitude, latitude, distanceInMeters);
    }

    public boolean existsById(String userId) {
        return locationRepository.existsById(userId);
    }

    public Optional<Location> findById(String userId) {
        return locationRepository.findById(userId);
    }
    public Page<ProfileModel> searchByMail(String mail, Pageable pageable) {
        return profileRepository.findByMail(mail, pageable);
    }
}