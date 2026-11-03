package com.scalux.stockManagement.security.services.IMPL;

import com.scalux.stockManagement.security.exceptions.UsernameNotExistException;
import com.scalux.stockManagement.security.models.entities.User;
import com.scalux.stockManagement.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotExistException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotExistException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}