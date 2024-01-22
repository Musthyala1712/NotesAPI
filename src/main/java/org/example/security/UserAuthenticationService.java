package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.model.PrincipalUser;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserAuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username - " + username);
        Optional<User> foundUser = userRepository.findByEmail(username);
        if (foundUser.isPresent()) {
            return new PrincipalUser(foundUser.get());
        }
        throw new UsernameNotFoundException("User not found!");
    }


}
