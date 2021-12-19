package org.gso.brinder.match.service;


import org.gso.brinder.match.model.ProfileModel;
import org.gso.brinder.match.repository.ProfileRepository;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private MongoOperations mongoOperations;
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {this.profileRepository = profileRepository;}

    public ProfileModel saveProfile(ProfileModel profile) {
        return profileRepository.save(profile);
    }

    public List<ProfileModel> findAll() {
        return profileRepository.findAll();
    }

    public List<ProfileModel> findByDistance(float longitude, float latitude, int distance) {
        Point base = new Point(longitude,latitude);
        Distance radius = new Distance(distance, Metrics.MILES);
        Circle area = new Circle(base, radius);

        Query query = new Query();
        query.addCriteria(Criteria.where("address.geoLocation").withinSphere(area));
        return mongoOperations.find(query, ProfileModel.class);
    }

    public ProfileModel getProfileByEmail(String mail) {
        return profileRepository.findByEmail(mail);
    }
}
