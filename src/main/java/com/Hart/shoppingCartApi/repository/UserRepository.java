package com.Hart.shoppingCartApi.repository;

import com.Hart.shoppingCartApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
