package org.gso.brinder.match.repository;


import org.gso.brinder.match.controller.MatchingProfileController;
import org.gso.brinder.match.model.MatchedUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends MongoRepository<MatchedUser, String> {
}
