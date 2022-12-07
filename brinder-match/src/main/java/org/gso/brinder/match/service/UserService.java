package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUser(JwtAuthenticationToken token) {
        return userRepository.findById(token.getTokenAttributes().get("id").toString()).orElse(null);
    }

    // METTRE A JOUR LES COORDONNEES
    public void updateUserLocation(JwtAuthenticationToken token,@RequestBody Coordonnee coordonnee) {
        userRepository.save(new User(token.getTokenAttributes("id").get(),
                token.getTokenAttributes("firstname").get(),
                token.getTokenAttributes("lastname").get(),
                coordonnee.getLocation()[0],
                coordonnee.getLocation()[1]));
    }
    // RECUPERER LES USERS 100M AUX ALENTOURS
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token,@RequestBody Coordonnee coordonnee) {
        List<GeoResult<User>> results = userRepository.findByLocationNear(
                new Point(coordonnee.getLocation()[0],coordonnee.getLocation()[1]), new Distance(100)
        ).getContent();
        List<User> returned_result = null;
        for (GeoResult<User> result : results) {
            returned_result.add(result.getContent());
        }
        return returned_result;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

}
