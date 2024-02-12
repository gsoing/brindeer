package org.gso.brinder.match.service;

import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.repository.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private UserLocationRepository userLocationRepository;

    public void updateUserLocation(String userId, double latitude, double longitude) {
        UserLocation location = new UserLocation();
        location.setUserId(userId);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        userLocationRepository.save(location);
    }

    // Méthode pour récupérer la localisation d'un utilisateur
}
