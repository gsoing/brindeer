package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.example.demo.model.Match;
import com.example.demo.repository.MatchRepository;


@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Match> findMatchesAroundMatchId(String matchId) {
        Match match = matchRepository.findMatchById(matchId);
        System.out.println(match);
        System.out.println(matchId);
        if (match != null) {
            List<Match> matchesAround = matchRepository.findUsersAroundLocation(
                match.getLocation().getX(),
                match.getLocation().getY()  
            );
            return matchesAround;
        }
        return null;
    }
    
    // obligé de créé cette méthode car la méthode save() de MongoRepository me créé un nouveau document
    // au lieu de le mettre à jour
    public void updateMatchLocation(String matchId, GeoJsonPoint newLocation) {
        Query query = new Query(Criteria.where("_id").is(matchId));
        Update update = new Update().set("location", newLocation);
        mongoTemplate.updateFirst(query, update, Match.class);
    }

    public boolean updateMatchLocation(String matchId, double latitude, double longitude) {
        @SuppressWarnings("null")
        Match match = matchRepository.findById(matchId).orElse(null);
        System.out.println(match);
        System.out.println(matchId);
        if (match != null) {
          GeoJsonPoint newLocation = new GeoJsonPoint(longitude, latitude);
          match.setLocation(newLocation);
          updateMatchLocation(matchId, newLocation);

          return true;
        } else {
          return false;
        }
      }
      
}
