package com.jbruell.donutpriorityqueue.web.controller;

import com.jbruell.donutpriorityqueue.model.Order;
import com.jbruell.donutpriorityqueue.service.OrderService;
import com.jbruell.donutpriorityqueue.web.dto.CreateOrderDto;
import com.jbruell.donutpriorityqueue.web.dto.OrderDto;
import com.jbruell.donutpriorityqueue.web.dto.OrderStatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    List<OrderStatusDto> getAllOrders() {
        List<OrderStatusDto> responseBody = new ArrayList<>();
        List<Order> queryResult = orderService.findAll();
        long now = Instant.now().toEpochMilli();
        for (int i = 0; i < queryResult.size(); i++) {
            Order o = queryResult.get(i);
            responseBody.add(new OrderStatusDto(o.getClientId(), o.getQuantity(), i + 1, now - o.getTimestamp()));
        }
        return responseBody;
    }

    @GetMapping("/nextdelivery")
    List<OrderDto> getNextDelivery() {
        return orderService.getNextOrderBatch().stream()
                .map(order -> new OrderDto(order.getClientId(), order.getQuantity())).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createOrder(@RequestBody CreateOrderDto dto) {
        orderService.createOrder(dto);
    }

    @DeleteMapping("/{clientId}")
    void cancelOrder(@PathVariable int clientId) {
        orderService.cancelOrder(clientId);
    }

    /* **********************************
     * Public endpoints for clients -->
     * **********************************/

    @GetMapping("/{clientId}")
    OrderStatusDto getOrderStatus(@PathVariable int clientId) {
        List<Order> orders = orderService.findAll();
        long now = Instant.now().toEpochMilli();

        int index = IntStream.range(0, orders.size())
                .filter(i -> orders.get(i).getClientId() == clientId).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Order o = orders.get(index);
        return new OrderStatusDto(o.getClientId(), o.getQuantity(), index + 1, now - o.getTimestamp());
    }
}
