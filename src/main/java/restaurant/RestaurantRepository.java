package restaurant;

import common.BaseRepository;
import lombok.*;

import java.util.Optional;
import java.util.UUID;
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class RestaurantRepository extends BaseRepository<Restaurant, UUID> {
    private static final RestaurantRepository repository = new RestaurantRepository();

    public static RestaurantRepository getInstance() {
        return repository;
    }

    public Optional<Restaurant> findByName(String name) {
        return findAll().stream()
                .filter(restaurant -> restaurant.getName().equals(name))
                .findFirst();
    }
}
