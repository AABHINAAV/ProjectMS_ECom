package com.learnMS.Order.service.implementation;

import com.learnMS.Order.dto.OrderLineItemsDto;
import com.learnMS.Order.dto.OrderRequest;
import com.learnMS.Order.dto.response.InventoryResponse;
import com.learnMS.Order.dto.response.OrderLineItemResponse;
import com.learnMS.Order.dto.response.OrderResponse;
import com.learnMS.Order.model.Order;
import com.learnMS.Order.model.OrderLineItems;
import com.learnMS.Order.repository.OrderRepository;
import com.learnMS.Order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(get_orderLineItems_From_orderLineItemsDtos(orderRequest))
                .build();

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(orderLineItems -> orderLineItems.getSkuCode())
                .toList();

        log.info(skuCodes.toString());

//        call inventory service and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8003/api/inventory/checkAllInStock",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponses.length == 0) {
            throw new IllegalArgumentException("Product is not in stock. Please try again later!!");
        }

        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> inventoryResponse.getQuantity() > 0);

        if (allProductsInStock == true) {
            this.orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock. Please try again later!!");
        }
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
