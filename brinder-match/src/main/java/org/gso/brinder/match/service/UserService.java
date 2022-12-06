package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUser(JwtAuthenticationToken token) {
        return userRepository.findById(token.getTokenAttributes().get("").toString()).orElse(null);
    }

    // METTRE A JOUR LES COORDONNEES
    public void updateUserLocation(JwtAuthenticationToken token) {

    }
    // RECUPERER LES USERS 100M AUX ALENTOURS
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token) {
        List<User> surrounding_users = userRepository.findAll();
        return  surrounding_users;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

}
