package org.gso.brinder.match.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser(JwtAuthenticationToken token) {
        return userRepository.findById(token.getTokenAttributes().get("sub").toString()).orElse(null);
    }

    // METTRE A JOUR LES COORDONNEES
    public User updateUserLocation(JwtAuthenticationToken token) {
        Coordonnee coordinate = addressToCoordinate(token);
        Point point = new Point(coordinate.getLocation()[0],coordinate.getLocation()[1]);
       return userRepository.save(new User(token.getTokenAttributes().get("sub").toString(),
                token.getTokenAttributes().get("given_name").toString(),
                token.getTokenAttributes().get("family_name").toString(),
                token.getTokenAttributes().get("email").toString(),
                (Integer) token.getTokenAttributes().get("age"),
                point));
    }

    public void updateUserLocation(JwtAuthenticationToken token, String address) {
        Coordonnee coordinate = addressToCoordinate(address);
        Point point = new Point(coordinate.getLocation()[0],coordinate.getLocation()[1]);
        userRepository.save(new User(token.getTokenAttributes().get("sub").toString(),
                token.getTokenAttributes().get("given_name").toString(),
                token.getTokenAttributes().get("family_name").toString(),
                token.getTokenAttributes().get("email").toString(),
                (Integer) token.getTokenAttributes().get("age"),
                point));
    }

    public Coordonnee addressToCoordinate(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyC6M0Wt1zio5q8b5ZfQYiNjZU7OVE4s72s&address="
                + java.net.URLEncoder.encode(address, StandardCharsets.UTF_8).replace("+", "%20");
        logger.info(url);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Map.class
        );
        System.out.println("resultat : " + result.getBody().toString());
//        double latitude = result.getJSONObject("results").getJSONObject("0").getJSONObject("geometry").getJSONObject("location").get("lat");
//        double longitude = result.getJSONObject("results").getJSONObject("0").getJSONObject("geometry").getJSONObject("location").get("lng")
      /*  double latitude = (double) result.get("lat"); double longitude = (double) result.get("lng");
        logger.info("latitude = " + latitude + ", longitude = " + longitude);*/
        return new Coordonnee(48.8,2.3);
    }

    public Coordonnee addressToCoordinate(JwtAuthenticationToken token) {
        String address = (String) token.getTokenAttributes().get("address");
        return addressToCoordinate(address);
    }

    // RECUPERER LES USERS 100M AUX ALENTOURS
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token) {
        Coordonnee coordonnee = addressToCoordinate(token);
        List<GeoResult<User>> results = userRepository.findByLocationNear(new Point(coordonnee.getLocation()[0],coordonnee.getLocation()[1]), new Distance(100)).getContent();
        List<User> returned_result = null;
        for (GeoResult<User> result : results) {
            if (result.getContent().getId() != token.getTokenAttributes().get("sub"))
                returned_result.add(result.getContent());
       }
        return returned_result;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

}
