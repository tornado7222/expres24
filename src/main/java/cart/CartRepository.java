package cart;

import common.BaseRepository;
import lombok.*;

import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartRepository extends BaseRepository<Cart, UUID> {
    private static final CartRepository repository = new CartRepository();

    public static CartRepository getInstance() {
        return repository;
    }
}
