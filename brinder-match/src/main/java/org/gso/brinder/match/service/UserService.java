package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser(JwtAuthenticationToken token) {
        return userRepository.findById(token.getTokenAttributes().get("sub").toString()).orElse(null);
    }

    // METTRE A JOUR LES COORDONNEES
    public void updateUserLocation(JwtAuthenticationToken token,@RequestBody Coordonnee coordonnee) {

        userRepository.save(new User(token.getTokenAttributes().get("sub").toString(),
                token.getTokenAttributes().get("given_name").toString(),
                token.getTokenAttributes().get("family_name").toString(),
                token.getTokenAttributes().get("email").toString(),
                (Integer) token.getTokenAttributes().get("age"),
                (double) coordonnee.getLocation()[0],
                (double) coordonnee.getLocation()[1]));
    }
    // RECUPERER LES USERS 100M AUX ALENTOURS
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token,@RequestBody Coordonnee coordonnee) {
        List<GeoResult<User>> results = userRepository.findByLocationNear(
                new Point(coordonnee.getLocation()[0],coordonnee.getLocation()[1]), new Distance(100)
        ).getContent();
        List<User> returned_result = null;
        for (GeoResult<User> result : results) {
            if (result.getContent().getId() != token.getTokenAttributes().get("sub"))
                returned_result.add(result.getContent());
        }
        return returned_result;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

}
