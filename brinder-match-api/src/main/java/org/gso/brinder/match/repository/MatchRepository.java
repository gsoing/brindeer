package org.gso.brinder.match.repository;

import java.util.Optional;
import java.util.List;

import org.gso.brinder.match.model.ProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.geo.Distance;

@Repository
public interface MatchRepository
        extends PagingAndSortingRepository<ProfileModel, String>, CrudRepository<ProfileModel, String> {

    Optional<ProfileModel> findByMail(String email);

    // Find profiles around 100m of the provided GeoJsonPoint
    @Query("{ 'location' : { $near : { $geometry : { type : 'Point' , coordinates : [ ?0 , ?1 ] } , $maxDistance : ?2 } } }")
    List<ProfileModel> findProfilesNearLocation(double longitude, double latitude, Distance maxDistance);
}
