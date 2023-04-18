package com.stp.springsecurityjwtdbcontainer.SecurityLayer;


import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.DAO.Role;
import com.stp.springsecurityjwtdbcontainer.Repositories.EnduserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationInitializer {

    @Bean
    public CommandLineRunner createDefaultAdminUser(EnduserRepository enduserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultAdminUsername = "admin";
            String defaultAdminPassword = "admin";

            if (!enduserRepository.findByUsername(defaultAdminUsername).isPresent()) {
                Enduser admin = Enduser.builder()
                        .username(defaultAdminUsername)
                        .email("admin@example.com")
                        .password(passwordEncoder.encode(defaultAdminPassword))
                        .role(Role.ADMIN)
                        .build();

                enduserRepository.save(admin);
            }
        };
    }

    @Bean
    public CommandLineRunner createDefaultUser(EnduserRepository enduserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultUserUsername = "user";
            String defaultUserPassword = "user";

            if (!enduserRepository.findByUsername(defaultUserUsername).isPresent()) {
                Enduser user = Enduser.builder()
                        .username(defaultUserUsername)
                        .email("user@example.com")
                        .password(passwordEncoder.encode(defaultUserPassword))
                        .role(Role.USER)
                        .build();

                enduserRepository.save(user);
            }
        };
    }

    @Bean
    public CommandLineRunner createDefaultMod(EnduserRepository enduserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultModUsername = "mod";
            String defaultUserPassword = "mod";

            if (!enduserRepository.findByUsername(defaultModUsername).isPresent()) {
                Enduser user = Enduser.builder()
                        .username(defaultModUsername)
                        .email("user@example.com")
                        .password(passwordEncoder.encode(defaultUserPassword))
                        .role(Role.MODERATOR)
                        .build();

                enduserRepository.save(user);
            }
        };
    }

}