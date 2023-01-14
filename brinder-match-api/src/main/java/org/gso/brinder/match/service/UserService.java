package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final int DISTANCE = 100;

    public User updateLocation(JwtAuthenticationToken principal) {
        Point point = new Point(Double.parseDouble(principal.getTokenAttributes().get("longitude").toString()),
                Double.parseDouble(principal.getTokenAttributes().get("latitude").toString()));

        User user = new User(
                principal.getTokenAttributes().get("sub").toString(),
                principal.getTokenAttributes().get("given_name").toString(),
                principal.getTokenAttributes().get("family_name").toString(),
                principal.getTokenAttributes().get("email").toString(),
                Integer.parseInt(principal.getTokenAttributes().get("age").toString()),
                point);

        return userRepository.save(user);
    }

    public List<User> searchAroundLocation(JwtAuthenticationToken principal) {
        Point point = new Point(Double.parseDouble(principal.getTokenAttributes().get("longitude").toString()),
                Double.parseDouble(principal.getTokenAttributes().get("latitude").toString()));

        List<GeoResult<User>> res = userRepository.findByLocationNear(point, new Distance(DISTANCE)).getContent();
        String id = principal.getTokenAttributes().get("sub").toString();

        List<User> users = res.stream()
                .map(GeoResult::getContent)
                .filter(user -> user.getId() != id)
                .collect(Collectors.toList());

        return users;
    }
}