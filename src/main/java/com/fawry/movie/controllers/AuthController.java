package com.fawry.movie.controllers;

import com.fawry.movie.entities.User;
import com.fawry.movie.secruity.CustomUserDetailsService;
import com.fawry.movie.secruity.JwtUtil;
import com.fawry.movie.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder; // 👈 بنستخدمه في تشفير الباسورد

    /*
    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(Map.of("jwt", jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    // ✅ Register endpoint
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // 👇 هنا بيتخزن مشفر
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }
     */

    // تسجيل
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // شفر الباسورد قبل ما تحفظه
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        // احفظ اليوزر
         userService.addUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // لوجين
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // تأكد من اليوزر والباسورد
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            // رجّع الـ UserDetails
            UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(authRequest.getUsername());
              System.out.println(" userDetails are "+ userDetails);

            // اعمل JWT
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            System.out.println("The Jwt of this user is :" + jwt);
            return ResponseEntity.ok(Map.of("jwt", jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }


    // ✅ DTO for login
    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
