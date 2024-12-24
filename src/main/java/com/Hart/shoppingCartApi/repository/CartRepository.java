package com.Hart.shoppingCartApi.repository;

import com.Hart.shoppingCartApi.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

}
