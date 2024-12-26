package com.Hart.shoppingCartApi.repository;

import com.Hart.shoppingCartApi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Role findByName(String role);
    Optional<Role> findByName(String role);
}

