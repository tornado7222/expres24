package ui;

import cart.Cart;
import context.Context;
import product.Product;
import product.ProductService;
import restaurant.Restaurant;
import restaurant.RestaurantService;
import user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UserUI {
    private final ProductService productService = ProductService.getInstance();
    private final RestaurantService restaurantService = RestaurantService.getInstance();
    public void start(User user){
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1. Restaurantlar
                    2. Buyurtmalar
                    3. Balance
                    0. Ortga
                    . >>>\s""");
            int command = Context.intScanner.nextInt();
            switch (command) {
                case 1 -> {
                    showRestaurants(user);
                }
                case 2 -> {

                }
                case 3 -> {
                    showBalance(user);
                }
                case 0 -> isExited = true;
            }
        }
    }

    private void showRestaurants(User user) {
        List<Restaurant> all = restaurantService.findAll();

        AtomicInteger counter = new AtomicInteger();
        all.forEach(restaurant -> {
            System.out.println(counter.getAndIncrement() + ". " + restaurant.getName());
        } );

        System.out.println("Restaurantni tanlang: ");
        int index = Context.intScanner.nextInt();
        if (index >= 0 && index < all.size()){
            showProductByRestaurant(all.get(index), user);
        }else {
            System.out.println("Noto'g'ri tanladingiz");
        }
    }

    private void showProductByRestaurant(Restaurant restaurant, User user) {
        Cart cart = new Cart(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), user, user, user.getId(), restaurant.getId(), new ArrayList<>(), null);
        boolean isExited = false;

        while (!isExited) {
            List<Product> products = productService.findByRestaurantId(restaurant.getId());
            AtomicInteger indexCounter = new AtomicInteger();
            products.forEach(product -> System.out.println(indexCounter.getAndIncrement() + ". " + product.getName() + "\t" + product.getPrice()));

            System.out.print("""
                    < orqaga
                    > oldinga
                    # savatcha
                    >>>\s""");
            String command = Context.stringScanner.nextLine();
            switch (command){
                case "<" -> {
                    isExited = true;
                }
                case ">" -> {

                }
                case "#" -> {
                    List<UUID> selectedProductId = cart.getProducts();
                    AtomicReference<Double> counter = new AtomicReference<>((double) 0);
                    selectedProductId.stream()
                            .map(productService::findByID)
                            .map(Optional::get)
                            .map(Product::getPrice)
                            .forEach(price -> counter.updateAndGet(v ->(v + price)));

                    /*List<Product> selectedProducts =*/ selectedProductId.stream()
                            .map(productService::findByID)
                            .map(Optional::get)
                            .forEach(product -> {
                                System.out.println(product.getName() + "\t" + product.getPrice());
                            });
                    System.out.println("Jami summa " + counter);
                }
                default -> {
                    try {
                        int index = Integer.parseInt(command);
                        if (index >= 0 && index < products.size()) {
                            Product product = products.get(index);
                            cart.getProducts().add(product.getId());
                            System.out.println(product.getName() + " Savatga qo'shildi");
                        }else {
                            System.out.println("Noto'g'ri product tanlandingiz");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Siz noto'g'ri buyriq kiritdingiz");
                    }
                }
            }
        }
    }

    private void showBalance(User user){
        System.out.println("Your balance + " + user.getBalance());
    }
}
