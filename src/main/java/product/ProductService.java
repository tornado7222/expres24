package product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService {
    private static final ProductService productService = new ProductService();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    public List<Product> findByRestaurantId(UUID restaurantId){
        return productRepository.findByRestaurantId(restaurantId);
    }
    public static ProductService getInstance() {
        return productService;
    }

    public void add(Product product) {
        productRepository.add(product);
    }

    public Optional<Product> findByID(UUID productId) {
        return productRepository.findById(productId);
    }
}
