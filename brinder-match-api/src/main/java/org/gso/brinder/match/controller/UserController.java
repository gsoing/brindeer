package org.gso.brinder.match.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.service.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(

        value = UserController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH = "/api/v1/matchs";
    public static int MAX_PAGE_SIZE = 200;
    @Autowired
    private final UserService userService;

    @PostMapping("/users/update")
    public ResponseEntity<User> updateUserLocation(JwtAuthenticationToken token) throws JSONException {
        return ResponseEntity.ok(userService.updateUserLocation(token));
    }

    @GetMapping("/users/nearest")
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token) {
        return userService.searchSurroundingUsers(token);
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentUserProfile(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(principal);
    }

}

