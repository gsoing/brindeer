package org.gso.brinder.match.service;


import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.UserMatchProfile;
import org.gso.brinder.match.repository.MatchProfileRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
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
public class UserMatchProfileService {

    private final MatchProfileRepository matchProfileRepository;

    private final MongoTemplate mongoTemplate;

    public UserMatchProfileService(MatchProfileRepository matchProfileRepository, MongoTemplate mongoTemplate) {
        this.matchProfileRepository = matchProfileRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public UserMatchProfile getById(String idMatch){
        return matchProfileRepository.findById(idMatch).orElseThrow(
                () -> new IllegalStateException("this id " + idMatch + " is not found"));
    }

    public UserMatchProfile createLocationProfile(UserMatchProfile userMatchProfile){
        log.info("Saving new Location Profile {} into database", userMatchProfile.getIdMatchProfile());
        return matchProfileRepository.save(userMatchProfile);
    }

    public List<UserMatchProfile> findLocationsProfile(Pageable pageable){
        log.info("Getting all Location Profile in database");
        return matchProfileRepository.findAll();
    }


    public List<UserMatchProfile> findByDistance(String userId){
        int distance = 100;
        UserMatchProfile userMatchProfile = getById(userId);
        Point myPoint = new Point(userMatchProfile.getGeoLocation().getLatitude(), userMatchProfile.getGeoLocation().getLongitude());
        Distance radius = new Distance(distance, Metrics.MILES);
        Circle area = new Circle(myPoint, radius);
        Criteria myPlaceCriteria = Criteria.where("MatchProfile.geoLocation").within(area);
        Query query = new Query();
        query.addCriteria(myPlaceCriteria);
        return mongoTemplate.find(query, UserMatchProfile.class);
    }

    public UserMatchProfile updateProfileMatch(UserMatchProfile userMatchProfile){
        return matchProfileRepository.save(userMatchProfile);
    }
}
