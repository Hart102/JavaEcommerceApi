package com.Hart.shoppingCartApi.service.cart;

import com.Hart.shoppingCartApi.model.Cart;
import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
}
