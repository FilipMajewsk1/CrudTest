package org.example.services;

import org.example.models.Order;
import org.example.models.OrderStatus;
import org.example.repositories.OrderRepo;

import java.util.List;

public class OrderService {
    private final OrderRepo orderRepository;

    public OrderService(OrderRepo orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void cancelOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrderStatus(int id, OrderStatus status) {Order order = orderRepository.findById(id);
        if (order == null) {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
