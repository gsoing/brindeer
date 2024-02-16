package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.User;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    GeoResults<User> findByLocationNear(Point location, Distance distance);
}