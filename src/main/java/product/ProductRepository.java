package product;

import common.BaseRepository;
import lombok.*;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ProductRepository extends BaseRepository<Product, UUID> {
    private static final ProductRepository repository = new ProductRepository();
    public List<Product> findByRestaurantId(UUID id){
        return entities.stream()
                .filter(product -> product.getRestaurantId().equals(id))
                .toList();
    }

    public static ProductRepository getInstance() {
        return repository;
    }
}
