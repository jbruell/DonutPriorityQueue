package com.jbruell.donutpriorityqueue.repository;

import com.jbruell.donutpriorityqueue.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o, " +
            "(CASE WHEN (o.clientId < 1000) THEN 1 ELSE 0 END) AS premium " +
            "FROM Order o " +
            "ORDER BY premium DESC, o.timestamp ASC")
    List<Order> findAll(Pageable pageable);

    boolean existsByClientId(int clientId);

    void deleteByClientId(int clientId);

}
