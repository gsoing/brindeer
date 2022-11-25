package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.UserMatchProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchProfileRepository extends MongoRepository<UserMatchProfile, String> {


}
