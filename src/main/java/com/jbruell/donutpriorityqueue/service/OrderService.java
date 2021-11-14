package com.jbruell.donutpriorityqueue.service;

import com.jbruell.donutpriorityqueue.model.Order;
import com.jbruell.donutpriorityqueue.repository.OrderRepository;
import com.jbruell.donutpriorityqueue.web.dto.CreateOrderDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * @return all open orders, sorted by waiting time, with premium clients having higher priority
     * regardless of waiting time
     */
    public List<Order> findAll() {
        return orderRepository.findAll(Pageable.unpaged());
    }

    /**
     * Finds as many of the upcoming orders as will fit in the cart (max. 50 items).
     *
     * @return The list of orders to be handled with the next trip to the counter
     */
    public List<Order> getNextOrderBatch() {
        List<Order> nextOrderBatch = new ArrayList<>();
        int itemCount = 0;

        for (Order order : orderRepository.findAll(PageRequest.of(0, 50))) {
            if ((itemCount += order.getQuantity()) > 50) {
                break;
            }
            nextOrderBatch.add(order);
        }
        return nextOrderBatch;
    }

    /**
     * Create a new order for the client with the given id.
     * If there is already an order, this request fails with a 409.
     *
     * @param dto
     */
    public void createOrder(CreateOrderDto dto) {
        if (dto.clientId() == null || dto.quantity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The client id and quantity fields must not be null! Request body: " + dto);
        }
        try {
            orderRepository.save(new Order(dto.clientId(), dto.quantity(), Instant.now().toEpochMilli()));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An order for this client id already exists! Given id: " + dto.clientId());
        }
    }

    /**
     * Cancel the pending order for the client with the given id.
     *
     * @param clientId
     */
    @Transactional
    public void cancelOrder(int clientId) {
        if (!orderRepository.existsByClientId(clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        orderRepository.deleteByClientId(clientId);
    }
}
