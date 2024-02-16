package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Match;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    @Query(value = "{ '_id': ?0 }")
    Match findMatchById(String matchId);

    @Query(value = "{ 'location': { $near: { $geometry: { type: 'Point', coordinates: [ ?0, ?1 ] }, $maxDistance: 100 } } }")
    List<Match> findUsersAroundLocation(double longitude, double latitude);
}
