package com.example.google_auth_login.Controller;

import com.example.google_auth_login.Service.UserService;
import com.example.google_auth_login.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TestLoginController {

    private final UserService userService;

    public TestLoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home () {
        return "welcome back  !!! <a href = '/oauth2/authorization/google'> Login with google </a>";
    }

    @GetMapping("/profile")
    public Map<String,Object> profile (@AuthenticationPrincipal OAuth2User user ){
        return user.getAttributes();
    }

    @GetMapping("/token")
    public String token(
            @RegisteredOAuth2AuthorizedClient("google")
            OAuth2AuthorizedClient client
    ) {

        String accessToken = client.getAccessToken().getTokenValue();

        return accessToken;
    }
    @GetMapping("/db")
    public ResponseEntity<List<User>> allUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Admin dashboard";
    }
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {

        return Map.of(
                "name", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }

    @GetMapping("/l")
    public String logouted(){
        return "Logout Succesfull";
    }

}
