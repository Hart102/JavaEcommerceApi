package com.Hart.shoppingCartApi.service.order;

import com.Hart.shoppingCartApi.dto.OrderDto;
import com.Hart.shoppingCartApi.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    //============== Reused class ==============
    OrderDto convertToDto(Order order);
}
