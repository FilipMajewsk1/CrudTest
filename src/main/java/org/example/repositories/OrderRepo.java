package org.example.repositories;

import org.example.models.Order;

import java.util.List;

public interface OrderRepo {
    Order save(Order order);
    Order findById(int id);
    List<Order> findAll();
    void deleteById(int id);
}
