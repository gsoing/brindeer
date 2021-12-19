package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends MongoRepository<ProfileModel, String> {

}
