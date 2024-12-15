package com.Hart.shoppingCartApi.service.cart;

import com.Hart.shoppingCartApi.exception.ApplicationException;
import com.Hart.shoppingCartApi.model.Cart;
import com.Hart.shoppingCartApi.model.CartItem;
import com.Hart.shoppingCartApi.model.Product;
import com.Hart.shoppingCartApi.repository.CartItemRepository;
import com.Hart.shoppingCartApi.repository.CartRepository;
import com.Hart.shoppingCartApi.service.product.IProductService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);

        // 2. Get the product
        Product product = productService.getProductById(productId);
        if (product.getPrice() == null) {
            throw new ApplicationException("Product price cannot be null");
        }

        // 3. Check if the product already exists in the cart
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            // 4. If No, initiate a new cartItem entry
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cart.addItem(cartItem);
        } else {
            // 5. If Yes, increase product quantity with the requested quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        // 6. Set the total price
        cartItem.setTotoalPrice();

        //7. Set cart total amount
        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);

        // 8. Save the cart and cart item
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }


    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotoalPrice();
                });
        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ApplicationException("item not found"));
    }
}
