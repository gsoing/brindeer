package org.gso.brinder.match.brindermatchapi.repository;

import org.gso.brinder.match.brindermatchapi.model.ProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileModel, String> {

    @Query("SELECT p FROM ProfileModel u WHERE " +
            "FUNCTION('ST_DWithin', " +
            "FUNCTION('ST_GeomFromText', " +
            "CONCAT('POINT(', p.longitude, ' ', p.latitude, ')')" +
            "), " +
            "FUNCTION('ST_GeomFromText', " +
            "CONCAT('POINT(', :longitude, ' ', :latitude, ')')" +
            "), " +
            "100" +
            ") = TRUE")
    List<ProfileModel> findAround100m(@Param("longitude") double longitude, @Param("latitude") double latitude);

}
