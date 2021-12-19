package org.gso.brinder.match.repository;

import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileModel,String> {

    ProfileModel findByEmail(String mail);
}
