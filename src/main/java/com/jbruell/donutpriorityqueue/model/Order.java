package com.jbruell.donutpriorityqueue.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Persistable<Integer> {

    /**
     * Client IDs are in the range of 1 to 20000 and customers with IDs less than 1000 are
     * premium customers
     */
    @Id
    @Column(name = "clientid", unique = true)
    private int clientId;

    /**
     * The requested amount of donuts
     */
    @NotNull
    @Column(name = "quantity")
    private int quantity;

    /**
     * The epoch timestamp of when the order was created
     */
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;

    @Override
    public Integer getId() {
        return clientId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
