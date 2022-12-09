package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.service.UserService;
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

    private final UserService userService;

    @PostMapping("/users/update")
    public ResponseEntity<User> updateUserLocation(JwtAuthenticationToken token) { return ResponseEntity.ok(userService.updateUserLocation(token)); }

    @GetMapping("/users/nearest")
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token) { return userService.searchSurroundingUsers(token); }

    @GetMapping("/users")
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/address/{address}")
    private Coordonnee addressToCoordinate(@PathVariable("address") String address) { return userService.addressToCoordinate(address); }

<<<<<<< HEAD
    @GetMapping("/current")
    public ResponseEntity getCurrentUserProfile(JwtAuthenticationToken principal) {
        return ResponseEntity.ok(principal);
=======
    @GetMapping("/address")
    private Coordonnee addressToCoordinate(JwtAuthenticationToken token) { return userService.addressToCoordinate(token); }
>>>>>>> be080452e5864a09513f951f75779a94c331d245
    }
}
