package com.jbruell.donutpriorityqueue.web.dto;

public record OrderDto(int clientId, int quantity, int queuePosition, long waitTime) {
}
