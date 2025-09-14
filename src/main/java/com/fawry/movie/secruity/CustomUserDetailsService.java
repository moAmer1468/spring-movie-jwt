package com.fawry.movie.secruity;

import com.fawry.movie.entities.User;
import com.fawry.movie.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Trying to load user: " + username);

        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ User not found: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("✅ User found: " + user.getUsername() + " | Role: " + user.getRole().name());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                // authorities أفضل علشان Spring Security بيزود ROLE_ تلقائيًا مع hasRole()
                .authorities("ROLE_" + user.getRole().name())
                .build();

        System.out.println("📌 Built UserDetails: " + userDetails.getUsername() +
                " | Authorities: " + userDetails.getAuthorities());

        return userDetails;
    }
}
