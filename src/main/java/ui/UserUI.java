package ui;

import cart.Cart;
import context.Context;
import order.Order;
import order.OrderService;
import order.OrderStatus;
import product.Product;
import product.ProductService;
import restaurant.Restaurant;
import restaurant.RestaurantService;
import user.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UserUI {
    private final ProductService productService = ProductService.getInstance();
    private final RestaurantService restaurantService = RestaurantService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
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
                    showOrders(user);
                }
                case 3 -> {
                    showBalance(user);
                }
                case 0 -> isExited = true;
            }
        }
    }

    private void showOrders(User user) {
        List<Order> orders = orderService.findByUserId(user.getId());
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
            Restaurant restaurant = restaurantService.findById(order.getRestaurantId());
            System.out.println(restaurant.getName() + " restaurantidagi buyurtmalaringiz ");
            System.out.println(i + ". Buyurtma " + user.getName() + "Tomonidan " + order.getCreated().format(formatter) + " da yaratilgan " + "Holati " + order.getStatus());

            for (Product product : order.getProducts()) {
                System.out.println(product.getName() + "-------------" + product.getPrice());
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
                    createOrder(user, cart, restaurant);
                    isExited = true;
                }
                case "#" -> {
                    showCart(cart);
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

    private void createOrder(User user, Cart cart, Restaurant restaurant) {
        int sum = 0;
        List<Product> products = new ArrayList<>();
        for (UUID productId : cart.getProducts()) {
            Product product = productService.findByID(productId).get();
            products.add(product);
            sum += product.getPrice();
        }
        if (user.getBalance() > sum){
            user.setBalance(user.getBalance()-sum);
            Order order = new Order(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), user, user, user.getId(), restaurant.getId(), products, null, null, OrderStatus.NEW);
            orderService.addOrder(order);
            System.out.println("Siz mahsulotni sotib oldingiz");
        }else {
            System.out.println("Balansingiz yetmaydi " + user.getBalance());
            System.out.println("Buyurtmalar narxi " + sum);
        }
    }

    private void showCart(Cart cart) {
        boolean isExited = false;
        while (!isExited) {

            List<UUID> selectedProductId = cart.getProducts();
            AtomicReference<Double> counter = new AtomicReference<>((double) 0);

            List<Product> list = new ArrayList<>(selectedProductId.stream()
                    .map(productService::findByID)
                    .map(Optional::get)
                    .toList());

            list.stream()
                    .map(Product::getPrice)
                    .forEach(price -> counter.updateAndGet(v -> (v + price)));

            for (int i = 0; i < list.size(); i++) {
                Product product = list.get(i);
                System.out.println(i + ". " + product.getName() + "---------" + product.getPrice());
            }

            System.out.println("Jami summa " + counter);

            System.out.println("-1 orqaga");
            int command = Context.intScanner.nextInt();
            switch (command) {
                case -1 -> isExited = true;
                default -> {
                    if (command >= 0 && command < list.size()){
                        Product remove = list.remove(command);
                        cart.getProducts().remove(command);
                    }else {
                        System.out.println("Noto'g'ri");
                    }
                }
            }
        }
    }

    private void showBalance(User user){
        System.out.println("Your balance + " + user.getBalance());
    }
}
