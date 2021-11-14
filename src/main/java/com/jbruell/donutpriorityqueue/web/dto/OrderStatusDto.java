package com.jbruell.donutpriorityqueue.web.dto;

public record OrderStatusDto(Integer clientId, Integer quantity, Integer queuePosition, Long waitTime) {
}
