package com.learnMS.Order.service.implementation;

import com.learnMS.Order.dto.OrderLineItemsDto;
import com.learnMS.Order.dto.OrderRequest;
import com.learnMS.Order.dto.response.OrderLineItemResponse;
import com.learnMS.Order.dto.response.OrderResponse;
import com.learnMS.Order.model.Order;
import com.learnMS.Order.model.OrderLineItems;
import com.learnMS.Order.repository.OrderRepository;
import com.learnMS.Order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
//        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos()
//                .stream()
//                .map(orderLineItemsDto -> dtoToEntity(orderLineItemsDto))
//                .toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(get_orderLineItems_From_orderLineItemsDtos(orderRequest))
                .build();

        this.orderRepository.save(order);
    }

    private List<OrderLineItems> get_orderLineItems_From_orderLineItemsDtos(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(orderLineItemsDto -> dtoToEntity(orderLineItemsDto))
                .toList();
        return orderLineItems;
    }

    private OrderLineItems dtoToEntity(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .id(orderLineItemsDto.getId())
                .skuCode(orderLineItemsDto.getSkuCode())
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        List<OrderResponse> orderResponses = orders.stream()
                .map(order -> order_to_orderResponse(order))
                .toList();

        return orderResponses;
    }

    private OrderResponse order_to_orderResponse(Order order) {
        List<OrderLineItemResponse> orderLineItemResponses = order.getOrderLineItemsList()
                .stream()
                .map(orderLineItem -> orderLineItem_to_OrderLineItemResponse(orderLineItem))
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(orderLineItemResponses)
                .build();
    }

    private OrderLineItemResponse orderLineItem_to_OrderLineItemResponse(OrderLineItems orderLineItem) {
        return OrderLineItemResponse.builder()
                .id(orderLineItem.getId())
                .skuCode(orderLineItem.getSkuCode())
                .price(orderLineItem.getPrice())
                .quantity(orderLineItem.getQuantity())
                .build();
    }
}
