package order;

import common.BaseRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class OrderRepository extends BaseRepository<Order, UUID> {
    private static final OrderRepository repository = new OrderRepository();

    public static OrderRepository getInstance() {
        return repository;
    }

    public List<Order> findByUserId(UUID id) {
        List<Order> orders = new ArrayList<>();
        return orders = entities.stream()
                .filter(order -> order.getUserId().equals(id))
                .toList();
    }
}
