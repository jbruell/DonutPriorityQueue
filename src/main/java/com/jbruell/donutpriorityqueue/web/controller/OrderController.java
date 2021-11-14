package com.jbruell.donutpriorityqueue.web.controller;

import com.jbruell.donutpriorityqueue.web.dto.CreateOrderDto;
import com.jbruell.donutpriorityqueue.web.dto.OrderDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController("/orders")
public class OrderController {

    @GetMapping
    List<OrderDto> getAllOrders() {
        return Collections.emptyList(); // TODO
    }

    @GetMapping("/next")
    OrderDto getNextOrder() {
        return null; // TODO
    }

    @PostMapping
    void createOrder(@RequestBody CreateOrderDto dto) {
        // TODO
    }

    @DeleteMapping("/{clientId}")
    void cancelOrder(@PathVariable int clientId) {
        // TODO
    }

    /* **********************************
     * Public endpoints for clients -->
     * **********************************/

    @GetMapping("/{clientId}")
    OrderDto getOrderStatus(@PathVariable int clientId) {
        return null; // TODO
    }
}
