package com.reduxr.config;

import com.reduxr.model.Role;
import com.reduxr.model.RoleName;
import com.reduxr.model.User;
import com.reduxr.repository.RoleRepository;
import com.reduxr.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByEmailAndRolesName(adminEmail, RoleName.ADMIN)) {
            return;
        }

        if (userRepository.existsByRolesName(RoleName.ADMIN)) {
            throw new IllegalStateException("Admin email cannot be changed "
                    + "after the first admin is created");
        }

        if (userRepository.existsByEmail(adminEmail)) {
            throw new IllegalStateException("Configured admin email already belongs "
                    + "to a user without the ADMIN role");
        }

        Role role = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException(
                        RoleName.ADMIN + " role is missing"));

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setFirstName("Admin");
        admin.setLastName("Admin");

        admin.setRoles(Set.of(role));
        userRepository.save(admin);
    }
}
