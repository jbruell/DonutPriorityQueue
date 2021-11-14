package com.jbruell.donutpriorityqueue.model;

/**
 * @param clientId Client IDs are in the range of 1 to 20000 and customers with IDs less than 1000 are
 *                 premium customers
 * @param quantity The requested amount of donuts
 */
public record Order(int clientId, int quantity) {
}
