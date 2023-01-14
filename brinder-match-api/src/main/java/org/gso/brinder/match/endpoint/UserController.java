package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = UserController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH = "/api/v1/match";
    private final UserService userService;

    @PostMapping("/update")
    public ResponseEntity<User> updateLocation(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(userService.updateLocation(principal));
    }

    @GetMapping("/search")
    public List<User> searchAroundLocation(JwtAuthenticationToken principal) {
        return userService.searchAroundLocation(principal);
    }
}