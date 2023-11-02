package com.learnMS.Order.controller;

import com.learnMS.Order.dto.OrderRequest;
import com.learnMS.Order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.learnMS.Order.dto.response.OrderResponse;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "placeOrder_fallback")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    public String placeOrder_fallback(@RequestBody OrderRequest orderRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong. Please order after some time!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return this.orderService.getAllOrders();
    }
}
