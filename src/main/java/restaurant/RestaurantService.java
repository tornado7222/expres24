package restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RestaurantService {
    private static RestaurantService restaurantService = new RestaurantService();
    private final RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    public static RestaurantService getInstance() {
        return restaurantService;
    }
    public List<Restaurant> findAll(){
        return restaurantRepository.findAll();
    }
    public boolean add(Restaurant restaurant){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurant.getName());
        if (optionalRestaurant.isPresent()){
            return false;
        }
        restaurantRepository.add(restaurant);
        return true;
    }

    public Restaurant findById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId).get();
    }
}
