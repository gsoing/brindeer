package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.MatchModel;
import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
//TODO import org.gso.brinder.profile.model.ProfileModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<MatchModel, String> {

    @Query("{ userId: ?0 }")
    void updateUserLocation(String userId, double latitude, double longitude);

    @Query("{ location: { $near: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: 100 } } }")
    List<ProfileModel> findNearbyUsers(double latitude, double longitude);
}

