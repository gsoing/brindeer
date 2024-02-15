package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.UserLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserLocationRepository extends MongoRepository<UserLocation, String> {
    @Query("{'location': {$near: {$geometry: {type: 'Point', coordinates: [?0, ?1]}, $maxDistance: ?2}}}")
    List<UserLocation> findByLocationNear(double longitude, double latitude, double distance);
}
