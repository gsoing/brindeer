package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.UserLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserLocationRepository extends MongoRepository<UserLocation, String> {
    List<UserLocation> findByUserIdNear(String userId, double radius);
}
