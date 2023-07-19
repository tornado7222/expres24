package restaurant;

import common.BaseRepository;
import lombok.*;

import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class RestaurantRepository extends BaseRepository<Restaurant, UUID> {
    private static final RestaurantRepository repository = new RestaurantRepository();

    public static RestaurantRepository getInstance() {
        return repository;
    }
}
