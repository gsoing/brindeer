package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.MatchProfileModel;
import org.gso.brinder.match.repository.MatchRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MongoTemplate mongoTemplate;

    public MatchProfileModel updateLocation(MatchProfileModel profileToUpdate) {
        Optional<MatchProfileModel> profileModel = matchRepository.findById(profileToUpdate.getId());
        profileModel.get().setUserId(profileToUpdate.getUserId());
        return matchRepository.save(profileModel.get());
    }

    public List<MatchProfileModel> getMatches(double longitude, double latitude, double radius) {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("function('ST_DWithin',function('ST_GeomFromText',concat('point(',longitude,' ',latitude,')'))," +
                "function('ST_GeomFromText',concat('point(',"+longitude+",' ',"+latitude+",')')),").is("<="+radius));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, MatchProfileModel.class, "MatchProfile");
    }
}
