package order;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private static final OrderService orderService = new OrderService();
    private static OrderRepository orderRepository = OrderRepository.getInstance();

    public static OrderService getInstance() {
        return orderService;
    }

    public void addOrder(Order order) {
        orderRepository.add(order);
    }

    public List<Order> findByUserId(UUID id) {
        return orderRepository.findByUserId(id);
    }
}
