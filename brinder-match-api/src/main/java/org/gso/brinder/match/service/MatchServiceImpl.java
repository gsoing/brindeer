package org.gso.brinder.match.service;

import org.gso.brinder.match.model.Address;
import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.MatchRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    private MatchRepository matchRepository;

    private MongoOperations mongoOperations;

    public List<ProfileModel> getMatchesWithin100m(String profileId) {

        Optional< ProfileModel > optional = matchRepository.findById(profileId);
        ProfileModel profile;
        if (optional.isPresent()) {
            profile = optional.get();
        } else {
            throw new RuntimeException("Profile not found for id: " + profileId);
        }

        Double lat = profile.getAddress().getLocation().getLatitude();
        Double lon = profile.getAddress().getLocation().getLongitude();

        Point profilePoint = new Point(lon,lat);
        // Within a radius of 100 meters
        Distance radius = new Distance(0.1, Metrics.KILOMETERS);
        Circle area = new Circle(profilePoint, radius);

        Query query = new Query();
        query.addCriteria(Criteria.where("address.location").withinSphere(area));
        return mongoOperations.find(query, ProfileModel.class);

    }

    public String updateLocation (String profileId, Address address){

        Optional< ProfileModel > optional = matchRepository.findById(profileId);
        ProfileModel profile;
        if (optional.isPresent()) {
            profile = optional.get();
        } else {
            throw new RuntimeException("Profile not found for id: " + profileId);
        }

        profile.setAddress(address);
        matchRepository.save(profile);
        return JSONObject.valueToString("Your address has been updated with success !");
    }

    public ProfileModel saveProfile(ProfileModel profile) {
        return matchRepository.save(profile);
    }

}
