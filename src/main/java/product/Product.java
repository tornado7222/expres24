package product;

import common.BaseEntity;
import lombok.*;
import user.User;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Product extends BaseEntity<UUID> {
    private UUID restaurantId;
    private double price;
    private boolean isAvailable;

    public Product(UUID id, LocalDateTime created, LocalDateTime modified, User creatBy, User modifiedBy, UUID restaurantId, double price, boolean isAvailable) {
        super(id, created, modified, creatBy, modifiedBy);
        this.restaurantId = restaurantId;
        this.price = price;
        this.isAvailable = isAvailable;
    }
}
