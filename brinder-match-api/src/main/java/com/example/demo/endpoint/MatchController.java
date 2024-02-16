package com.example.demo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Match;
import com.example.demo.repository.MatchRepository;
import com.example.demo.services.MatchService;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchRepository matchRepository;

    @PutMapping("/update-location/{matchId}")
    public ResponseEntity<String> updateLocation(
            @PathVariable String matchId,
            @RequestParam double latitude,
            @RequestParam double longitude
            ){
                boolean updateSuccessful = matchService.updateMatchLocation(
                    matchId, 
                    latitude, 
                    longitude
                );
                System.out.println("bool updateSuccessful" + updateSuccessful);
        
                if(updateSuccessful) {
                    return ResponseEntity.ok("Localisation mise à jour avec succès.");
                } else {
                    return ResponseEntity.badRequest().body("Échec de la mise à jour de la localisation de l'utilisateur.");
                }
            }

    @GetMapping("/find-around/{matchId}")
    public ResponseEntity<List<Match>> findMatchesAroundMe(@PathVariable String matchId) {
        List<Match> matchAround = matchService.findMatchesAroundMatchId(matchId);
        System.out.println("matchAround" + matchAround);
        
        if(matchAround != null && !matchAround.isEmpty()) {
            return ResponseEntity.ok(matchAround);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get-all-match")
    public List<Match> getAllEntities() {
        List<Match> entities = matchRepository.findAll();
        return entities;
    }
}
