package org.gso.brinder.match.repository;

import java.util.List;

import org.gso.brinder.match.model.MatchedUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends MongoRepository<MatchedUser, String> {

    // Find users within a certain distance from a geo point
    // The query remains effective for finding users near a specific geographical point.
    @Query("{'geoCoordinates.location': {$near: {$geometry: {type: 'Point', coordinates: [?0, ?1]}, $maxDistance: ?2}}}")
    List<MatchedUser> findByLocationNear(double longitude, double latitude, double distance);

    // Optional: If you need pagination support for nearby searches
    // Example method signature with pagination support
    List<MatchedUser> findByGeoCoordinates_LocationNear(org.springframework.data.geo.Point location, Distance distance, Pageable pageable);
}


