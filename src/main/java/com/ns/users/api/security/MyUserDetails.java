package com.ns.users.api.security;

import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User appUser = userRepository.findByEmail(email);

        if (appUser == null) {
            throw new UsernameNotFoundException(ErrorMessages.ERROR_USER_DONT_EXIST);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(appUser.getPassword())
                .authorities(appUser.getAppUserRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
