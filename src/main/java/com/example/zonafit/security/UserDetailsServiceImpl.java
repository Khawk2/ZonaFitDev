package com.example.zonafit.security;

import com.example.zonafit.domain.model.User;
import com.example.zonafit.domain.enums.StatusUser;
import com.example.zonafit.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(getAuthorities(user))
            .accountExpired(false)
            .accountLocked(isAccountLocked(user))
            .credentialsExpired(false)
            .disabled(isDisabled(user))
            .build();
}


    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    private boolean isDisabled(User user) {
        return user.getStatus() != StatusUser.ACTIVE;
    }

    private boolean isAccountLocked(User user) {
        //preparado para futuro (bloqueos por intentos fallidos)
        return false;
    }
}
