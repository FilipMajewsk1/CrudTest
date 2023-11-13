package org.example.models;

import java.util.List;
import java.util.Objects;

public class Order {
    private Integer id;
    private Long clientId;
    private List<Product> products;
    private OrderStatus status;

    public Order() {
    }

    public Order(Integer id, Long clientId, List<Product> products, OrderStatus status) {
        this.id = id;
        this.clientId = clientId;
        this.products = products;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(clientId, order.clientId) &&
                Objects.equals(products, order.products) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, products, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", products=" + products +
                ", status=" + status +
                '}';
    }
}