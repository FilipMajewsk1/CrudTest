

import org.example.models.Order;
import org.example.models.OrderStatus;
import org.example.repositories.OrderRepo;
import org.example.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepo orderDatabaseMock;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderDatabaseMock = Mockito.mock(OrderRepo.class);
        orderService = new OrderService(orderDatabaseMock);
    }

    @Test
    void testPlaceOrder() {
        Order order = new Order(null, 1L, null, OrderStatus.NEW);
        when(orderDatabaseMock.save(order)).thenReturn(new Order(1, 1L, null, OrderStatus.NEW));

        Order result = orderService.placeOrder(order);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAllOrders() {
        List<Order> mockOrders = Arrays.asList(
                new Order(1, 1L, null, OrderStatus.NEW),
                new Order(2, 2L, null, OrderStatus.PROCESSING)
        );

        when(orderDatabaseMock.findAll()).thenReturn(mockOrders);

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
        assertEquals(1L, orders.get(0).getId());
        assertEquals(2L, orders.get(1).getId());
    }


    @Test
    void testDeleteOrder() {
        orderService.cancelOrder(1);

        verify(orderDatabaseMock).deleteById(1);
    }

    @Test
    void testUpdateOrderStatus() {
        Order mockOrder = new Order(1, 1L, null, OrderStatus.PROCESSING);
        when(orderDatabaseMock.findById(1)).thenReturn(mockOrder);

        orderService.updateOrderStatus(1, OrderStatus.DONE);

        verify(orderDatabaseMock).save(argThat(order -> order.getStatus() == OrderStatus.DONE));
    }

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void testUpdateOrderStatusForAllPossibleStatuses(OrderStatus newStatus) {
        Order initialOrder = new Order(1, 1L, null, OrderStatus.PROCESSING);
        when(orderDatabaseMock.findById(1)).thenReturn(initialOrder);

        when(orderDatabaseMock.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0, Order.class);
            savedOrder.setId(1);
            return savedOrder;
        });

        Order result = orderService.updateOrderStatus(1, newStatus);

        verify(orderDatabaseMock).save(argThat(order -> order.getStatus() == newStatus));
        assertEquals(newStatus, result.getStatus());
    }

    @Test
    void testUpdateOrderStatusThrowsExceptionForNonExistentOrder() {
        int nonExistentOrderId = 1;
        when(orderDatabaseMock.findById(nonExistentOrderId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateOrderStatus(nonExistentOrderId, OrderStatus.DONE);
        });
    }

    @ParameterizedTest
    @MethodSource("provideOrderUpdateScenarios")
    void testComplexOrderStatusUpdateScenarios(Order initialOrder, OrderStatus newStatus, Class<? extends Exception> expectedException) {
        if (initialOrder == null) {
            when(orderDatabaseMock.findById(anyInt())).thenReturn(null);
        } else {
            when(orderDatabaseMock.findById(initialOrder.getId())).thenReturn(initialOrder);
            when(orderDatabaseMock.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0, Order.class));
        }

        if (expectedException != null) {
            assertThrows(expectedException, () -> orderService.updateOrderStatus(initialOrder != null ? initialOrder.getId() : 1, newStatus));
        } else {
            Order updatedOrder = orderService.updateOrderStatus(initialOrder.getId(), newStatus);
            assertEquals(newStatus, updatedOrder.getStatus());
        }
    }

    private static Stream<Arguments> provideOrderUpdateScenarios() {
        return Stream.of(
                Arguments.of(new Order(1, 1L, null, OrderStatus.PROCESSING), OrderStatus.DONE, null),
                Arguments.of(new Order(2, 1L, null, OrderStatus.DONE), OrderStatus.PROCESSING, null),
                Arguments.of(null, OrderStatus.PROCESSING, IllegalArgumentException.class),
                Arguments.of(new Order(3, 1L, null, OrderStatus.PROCESSING), OrderStatus.PROCESSING, null)
        );
    }
}

