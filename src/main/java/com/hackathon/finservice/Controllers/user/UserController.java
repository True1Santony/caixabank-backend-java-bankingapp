package com.hackathon.finservice.Controllers.user;


import com.hackathon.finservice.DTO.JwtResponse;
import com.hackathon.finservice.DTO.LoginRequest;
import com.hackathon.finservice.DTO.UserRegistrationRequest;
import com.hackathon.finservice.DTO.UserRegistrationResponse;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Util.JwtUtils;
import com.hackathon.finservice.Service.TokenService;
import com.hackathon.finservice.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationRequest request) {
       UserRegistrationResponse response = userService.registerUser(request);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getIdentifier(),
                            loginRequest.getPassword()
                    )
            );
            String jwt = jwtUtils.generateToken(loginRequest.getIdentifier());

            User user = userService.getUserByEmail(loginRequest.getIdentifier());

            tokenService.saveToken( user,jwt);

            return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        tokenService.revokeAllTokensByUser(
                userService.getUserByEmail(
                        authentication.getName()
                )
        );

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully");
    }

}
