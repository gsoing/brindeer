package org.gso.brinder.match.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.User;
import org.gso.brinder.match.service.UserService;
import org.springframework.http.MediaType;
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
    public void updateUserLocation(JwtAuthenticationToken token) { userService.updateUserLocation(token); }

    @PostMapping("/users/update")
    public void updateUserLocation(JwtAuthenticationToken token, @RequestParam String new_address) { userService.updateUserLocation(token, new_address); }

    @GetMapping("/users/nearest")
    public List<User> searchSurroundingUsers(JwtAuthenticationToken token) { return userService.searchSurroundingUsers(token); }

    @GetMapping("/users")
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/address/{address}")
    private Coordonnee addressToCoordinate(@PathVariable("address") String address) { return userService.addressToCoordinate(address); }

    @GetMapping("/address")
    private Coordonnee addressToCoordinate(JwtAuthenticationToken token) { return userService.addressToCoordinate(token); }
    }
