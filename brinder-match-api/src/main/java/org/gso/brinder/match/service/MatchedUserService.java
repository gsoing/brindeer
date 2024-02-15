package org.gso.brinder.match.service;

import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.GeoCoordinates;
import org.gso.brinder.match.model.MatchedUser;
import org.gso.brinder.match.repository.MatchingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class MatchedUserService {

    private final MatchingRepository matchingRepository;
    private final MongoTemplate mongoTemplate;

    public MatchedUserService(MatchingRepository matchingRepository, MongoTemplate mongoTemplate) {
        this.matchingRepository = matchingRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public MatchedUser getById(String idMatch) {
        return matchingRepository.findById(idMatch)
                .orElseThrow(() -> new IllegalStateException("User Match Profile not found with id: " + idMatch));
    }

    public MatchedUser createOrUpdateMatchedUserProfile(MatchedUser matchedUser) {
        log.info("Saving or Updating User Match Profile with id: {}", matchedUser.getIdMatchedUser());
        return matchingRepository.save(matchedUser);
    }

    public List<MatchedUser> findAllMatchedUserProfiles(Pageable pageable) {
        log.info("Fetching all User Match Profiles with pagination");
        return matchingRepository.findAll(pageable).getContent();
    }

    public List<MatchedUser> findMatchedUsersNearby(String userId, double distanceInKilometers) {
        MatchedUser matchedUser = getById(userId);
        GeoCoordinates geo = matchedUser.getGeoCoordinates();
        Point point = new Point(geo.getLongitude(), geo.getLatitude());
        Distance distance = new Distance(distanceInKilometers, Metrics.KILOMETERS);
        Query query = new Query(Criteria.where("geoCoordinates.location").nearSphere(point).maxDistance(distanceInKilometers / 111.12)); // Approx conversion factor for degrees to kilometers
        log.info("Finding Matched Users within {} kilometers for user ID: {}", distanceInKilometers, userId);
        return mongoTemplate.find(query, MatchedUser.class);
    }
}
