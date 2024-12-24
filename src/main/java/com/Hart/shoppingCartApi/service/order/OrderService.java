package com.Hart.shoppingCartApi.service.order;

import com.Hart.shoppingCartApi.dto.OrderDto;
import com.Hart.shoppingCartApi.enums.OrderStatus;
import com.Hart.shoppingCartApi.exception.ApplicationException;
import com.Hart.shoppingCartApi.model.Cart;
import com.Hart.shoppingCartApi.model.Order;
import com.Hart.shoppingCartApi.model.OrderItem;
import com.Hart.shoppingCartApi.model.Product;
import com.Hart.shoppingCartApi.repository.CartRepository;
import com.Hart.shoppingCartApi.repository.OrderRepository;
import com.Hart.shoppingCartApi.repository.ProductRepository;
import com.Hart.shoppingCartApi.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        // Clear Cart
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
              order,
              product,
              cartItem.getQuantity(),
              cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        // Convert single item to Dto
        return orderRepository.findById(orderId).map(this:: convertToDto).orElseThrow(() -> new ApplicationException("Order not found!"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList(); // Convert multiple items to Dto
    }

    //============== Reused class ==============
    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
