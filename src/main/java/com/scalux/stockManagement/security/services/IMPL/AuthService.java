    package com.scalux.stockManagement.security.services.IMPL;

    import com.scalux.stockManagement.security.exceptions.OldPasswordIncorrectException;
    import com.scalux.stockManagement.security.exceptions.UserIsActiveException;
    import com.scalux.stockManagement.security.exceptions.UserProfileNotFound;
    import com.scalux.stockManagement.security.mappers.UserMapper;
    import com.scalux.stockManagement.security.models.dtos.JwtResponseDto;
    import com.scalux.stockManagement.security.models.dtos.LoginRequestDto;
    import com.scalux.stockManagement.security.models.dtos.UserDto;
    import com.scalux.stockManagement.security.models.entities.User;
    import com.scalux.stockManagement.security.repositories.UserRepository;
    import com.scalux.stockManagement.security.services.IAuthService;
    import com.scalux.stockManagement.security.utils.JwtUtils;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    
    @Service
    @RequiredArgsConstructor
    public class AuthService implements IAuthService {
        private final UserRepository repository;
        private final UserMapper mapper;
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final PasswordEncoder passwordEncoder;

        @Override
        public JwtResponseDto login(LoginRequestDto dto) {
            Authentication authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );

            User user = getUserByUsername(dto.username());

//            if (!user.getIsActive()){
//                throw new UserIsActiveException(dto.username());
//            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateJwtToken(authentication);
            return new JwtResponseDto(token, user.getId(), dto.username(), user.getRole());
        }

        @Override
        public UserDto getAuthenticatedUserProfile() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof UserDetailsImpl userDetails) {
                    User authenticatedUser = getUserByUsername(userDetails.getUsername());
                    return mapper.toDto(authenticatedUser);
                } else {
                    throw new UserProfileNotFound("Authenticated user not found or not recognized.");
                }
            } else {
                throw new UserProfileNotFound("No authenticated user found.");
            }
        }

        private User getAuthenticatedUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof UserDetailsImpl userDetails) {
                    return getUserByUsername(userDetails.getUsername());
                } else {
                    throw new UserProfileNotFound("Authenticated user not found or not recognized.");
                }
            } else {
                throw new UserProfileNotFound("No authenticated user found.");
            }
        }

        @Override
        public void changePassword(String oldPassword, String newPassword) {
            User user = getAuthenticatedUser();

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new OldPasswordIncorrectException("Old password is incorrect");
            }

            if (oldPassword.matches(newPassword)){
                throw new OldPasswordIncorrectException("old password and new password are the same");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            repository.save(user);
        }

        private User getUserByUsername(String username) {
            return repository.findByUsername(username)
                    .orElseThrow(() -> new UserProfileNotFound("This username <" + username + "> is incorrect."));
        }
    }