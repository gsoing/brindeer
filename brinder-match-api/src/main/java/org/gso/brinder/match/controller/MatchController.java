package org.gso.brinder.match.controller;

import org.gso.brinder.match.model.UserLocation;
import org.gso.brinder.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<UserLocation>> findMatches(@RequestParam String userId, @RequestParam(defaultValue = "100") double radius) {
        List<UserLocation> matches = matchService.findMatches(userId, radius);
        return ResponseEntity.ok(matches);
    }
}
