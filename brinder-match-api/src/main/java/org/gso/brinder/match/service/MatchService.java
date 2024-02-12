package org.gso.brinder.match.service;

import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.repository.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatchService {

    private final UserLocationRepository userLocationRepository;

    @Autowired
    public MatchService(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
    }

    public List<UserLocation> findMatches(String userId, double radius) {
        // Placeholder for actual implementation
        // You'll need to implement the logic to find users within the specified radius.
        // This might involve using MongoDB's geospatial queries or another approach based on your database choice.
        return userLocationRepository.findByUserIdNear(userId, radius);
    }
}

