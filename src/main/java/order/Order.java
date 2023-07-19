package order;

import common.BaseEntity;
import lombok.*;
import product.Product;
import user.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends BaseEntity<UUID> {
    private UUID userId;
    private UUID restaurantId;
    private List<Product> products;
    private UUID cookId;
    private UUID courierId;
    private OrderStatus status;
    public Order(UUID id, LocalDateTime created, LocalDateTime modified, User creatBy, User modifiedBy, UUID userId, UUID restaurantId, List<Product> products, UUID cookId, UUID courierId, OrderStatus status) {
        super(id, created, modified, creatBy, modifiedBy);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.products = products;
        this.cookId = cookId;
        this.courierId = courierId;
        this.status = status;
    }
}
