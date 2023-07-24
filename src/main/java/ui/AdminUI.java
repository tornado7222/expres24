package ui;

import context.Context;
import order.Order;
import order.OrderService;
import order.OrderStatus;
import product.Product;
import product.ProductService;
import user.Role;
import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminUI {
    OrderService orderService = OrderService.getInstance();
    UserService userService = UserService.getInstance();
    ProductService productService = ProductService.getInstance();
    public void start(User admin){
        boolean isExited = false;
        while (!isExited) {
            System.out.println("""
                    1. Cookni ro'yxatdan o'tkazish
                    2. Courierni ro'yxatdan o'tkazish
                    3. Barcha buyurtmalarni ko'rish
                    4. Buyurtmalarni oshpazga berish
                    5. Buyurtmalarni courierga berish
                    6. Yangi mahsulot qo'shish
                    7. Barcha mahsulotlarni ko'rish
                    8. ortga""");
            int command = Context.intScanner.nextInt();
            switch (command) {
                case 1 -> {
                    createCook(admin);
                }
                case 2 -> {
                    createCourier(admin);
                }
                case 3 -> {
                    allOrders(admin.getRestaurantId());
                }
                case 4 -> {
                    ordersToCook();
                }
                case 5 -> {
                    ordersToCourier();
                }
                case 6 -> {
                    createNewProduct(admin);
                }
                case 7 -> {
                    allProducts(admin.getRestaurantId());
                }
                default -> isExited = true;
            }
        }
    }

    private void allProducts(UUID restaurantId) {
        List<Product> allProducts = productService.findByRestaurantId(restaurantId);
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss");
            System.out.println(i + ". " + product.getName() + "\t" + product.getPrice() + "\t" + product.getCreated().format(formatter) + "\t"
            + product.getCreatBy().getName() + "\t" + product.getModified().format(formatter) + "\t" + product.getModifiedBy());
        }
    }

    private void createNewProduct(User admin) {

    }
    private void allOrders(UUID restaurantId) {
        List<Order> all = orderService.findAll();
        List<Order> list = new ArrayList<>();
        list = all.stream()
                .filter(order -> order.getRestaurantId().equals(restaurantId))
                .toList();
        for (Order order : list) {
            List<Product> products = order.getProducts();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println(i + ". " + product.getName() + "----------------" + product.getPrice());
            }
        }
    }

    private void createCourier(User admin) {

    }
    private void createCook(User admin) {
        System.out.print("Cookni telefon raqamini kiriting : ");
        String poneNumber = Context.stringScanner.nextLine();
        System.out.print("Cook ismini kiriting : ");
        String name = Context.stringScanner.nextLine();
        System.out.print("Cook familiyasini kiriting : ");
        String surname = Context.stringScanner.nextLine();
        System.out.print("Cook default paroli kiriting : ");
        String password = Context.stringScanner.nextLine();
        System.out.print("Cook maoshini kiriting : ");
        double salary = Context.intScanner.nextInt();

        User user = new User(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), admin, admin, name, surname, salary, poneNumber, password, Role.COOK, null, salary, admin.getRestaurantId(), null);
        userService.create(user);
    }

    private void ordersToCourier() {
        List<Order> all = orderService.findAll();
        List<Order> orderCourierList = all.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.READY_DELIVER))
                .filter(order -> order.getCourierId() == null)
                .toList();
        List<User> couriers = userService.getCourier();
        for (Order order : orderCourierList) {
            User courier = couriers.stream()
                    .filter(user -> user.getRestaurantId().equals(order.getRestaurantId()))
                    .findFirst()
                    .get();
            order.setCourierId(courier.getId());
            order.setStatus(OrderStatus.IS_COURIER);
        }
    }
    private void ordersToCook() {
        List<Order> all = orderService.findAll();
        List<Order> orderCookList = new ArrayList<>();
        orderCookList = all.stream()
                .filter(order -> order.getCookId() == null)
                .toList();
        List<User> cooks = userService.getCook();
        for (Order order : orderCookList) {
            User cook = cooks.stream()
                    .filter(user -> user.getRestaurantId().equals(order.getRestaurantId()))
                    .findFirst()
                    .get();
            order.setCookId(cook.getId());
            order.setStatus(OrderStatus.IS_COOK);
        }
    }
}
