package com.scalux.stockManagement.config;



import com.scalux.stockManagement.security.models.entities.User;
import com.scalux.stockManagement.security.models.enums.Role;
import com.scalux.stockManagement.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeders implements CommandLineRunner {

    private final UserRepository userRepository;

    @Value("${admin.password}")
    private String rawPassword;
    @Value("${admin.username}")
    private String username;

    @Override
    public void run(String... args){
        seedAdmin();
    }

    private void seedAdmin(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "admin";
        String hashedPassword = encoder.encode(rawPassword);
        if (userRepository.count() == 0) {
            User admin = new User();
//            admin.setFirstName("Admin");
//            admin.setLastName("Ladmin");

            admin.setUsername(username);
            admin.setPassword(hashedPassword);
//            admin.setCIN("JB567890");
//            admin.setPhone("+212 6396-21650");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setIsActive(true);

            userRepository.save(admin);
        }
    }



}
