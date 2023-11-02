package com.learnMS.Order.service;

import com.learnMS.Order.dto.OrderRequest;
import com.learnMS.Order.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    public String placeOrder(OrderRequest orderRequest);

    public List<OrderResponse> getAllOrders();
}
