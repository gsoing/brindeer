package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUser(JWTAuthenticationToken token) {
        userRepository.findById(token.getId());
    }

    // METTRE A JOUR LES COORDONNEES
    public void updateUser(JWTAuthenticationToken token) {

    }

    public List<User> searchSurroundingUsers(JWTAuthenticationToken token) {

    }

}
