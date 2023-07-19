package order;

import common.BaseRepository;
import lombok.*;

import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class OrderRepository extends BaseRepository<Order, UUID> {
    private static final OrderRepository repository = new OrderRepository();

    public static OrderRepository getInstance() {
        return repository;
    }
}
