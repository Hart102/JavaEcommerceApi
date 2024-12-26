package com.Hart.shoppingCartApi.Data;

import com.Hart.shoppingCartApi.model.Role;
import com.Hart.shoppingCartApi.model.User;
import com.Hart.shoppingCartApi.repository.RoleRepository;
import com.Hart.shoppingCartApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ROLE_ADMIN not found"));

        for (int i = 1; i <= 3; i++) {
            String defaultEmail = "user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("User " + i + " created successfully");
        }
    }


    private void createDefaultAdminIfNotExists() {
        // Handle the Optional<Role> for admin role
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ROLE_ADMIN not found"));

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";

            // Check if a user with this email already exists
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            // Create a new user
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456")); // Encode password
            user.setRoles(Set.of(adminRole)); // Set admin role

            // Save the user
            userRepository.save(user);
            System.out.println("Admin " + i + " created successfully");
        }
    }


    // Create default user role
    public void createDefaultRoleIfNotExits(Set<String> roles) {
        for(String role : roles) {
            Optional<Role> result = roleRepository.findByName(role);
            if(result.isEmpty()){
                Role newRole = new Role();
                newRole.setName(role);
                roleRepository.save(newRole);
            }
        }
    }
}
