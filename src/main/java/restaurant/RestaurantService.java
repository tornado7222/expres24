package restaurant;

import java.util.List;
import java.util.Optional;

public class RestaurantService {
    private static RestaurantService restaurantService = new RestaurantService();
    private final RestaurantRepository repository = RestaurantRepository.getInstance();

    public static RestaurantService getInstance() {
        return restaurantService;
    }
    public List<Restaurant> findAll(){
        return repository.findAll();
    }
    public boolean add(Restaurant restaurant){
        Optional<Restaurant> optionalRestaurant = repository.findByName(restaurant.getName());
        if (optionalRestaurant.isPresent()){
            return false;
        }
        repository.add(restaurant);
        return true;
    }
}
