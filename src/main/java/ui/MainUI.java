package ui;


import context.Context;
import product.Product;
import product.ProductService;
import restaurant.Restaurant;
import restaurant.RestaurantService;
import user.Role;
import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.time.LocalDateTime.now;

public class MainUI {
    {
        RestaurantService restaurantService = RestaurantService.getInstance();
        ProductService productService = ProductService.getInstance();

        Restaurant restaurant = new Restaurant(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(),null, null, "Oqtepa Yunusobod", "Yunusobod");
        Restaurant restaurant1 = new Restaurant(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(),null, null, "Milliy Taomlar", "Yunusobod");
        Restaurant restaurant2 = new Restaurant(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(),null, null, "Bellisimo ", "Yunusobod");

        restaurantService.add(restaurant);
        restaurantService.add(restaurant1);
        restaurantService.add(restaurant2);

        Product product = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant.getId(),"Lavash", 15, true);
        Product product1 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant.getId(),"Burger", 10, true);
        Product product2 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant.getId(),"Pepsi", 7, true);
        Product product3 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant1.getId(),"Osh", 15, true);
        Product product4 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant1.getId(),"Manti", 10, true);
        Product product5 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant1.getId(),"Sho'rva", 7, true);
        Product product6 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant1.getId(),"Choy", 1, true);
        Product product7 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant2.getId(),"Peperoni", 8, true);
        Product product8 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant2.getId(),"Combo", 10, true);
        Product product9 = new Product(UUID.randomUUID(), now(), now(), null, null, restaurant2.getId(),"Go'shtli", 15, true);

        productService.add(product);
        productService.add(product1);
        productService.add(product2);
        productService.add(product3);
        productService.add(product4);
        productService.add(product5);
        productService.add(product6);
        productService.add(product7);
        productService.add(product8);
        productService.add(product9);
    }
    private final UserService userService = new UserService();
    public void start(){
        boolean isExited = false;

        while (!isExited){
            System.out.print("""
                    1. Kirish
                    2. Ro'yhatdan o'tish
                    0. chiqish
                    . >>>\s""");
            switch (Context.intScanner.nextInt()) {
                case 1 -> {
                    signIn();
                }
                case 2 -> {
                    signUp();
                }
                case 0 -> isExited = true;
                default -> {
                    System.out.println("No");
                }
            }
        }
    }

    private void signUp() {
        System.out.print("Telefon raqam kiriting : ");
        String poneNumber = Context.stringScanner.nextLine();
        System.out.print("Ismingizni kiriting : ");
        String name = Context.stringScanner.nextLine();
        System.out.print("Familiyangizni kiriting : ");
        String surname = Context.stringScanner.nextLine();
        System.out.print("Parolinginz kiriting : ");
        String password = Context.stringScanner.nextLine();
        System.out.print("balance ni kiriting : ");
        double balance = Context.intScanner.nextInt();
        User user = User.builder().id(UUID.randomUUID())
                .phoneNumber(poneNumber)
                .name(name)
                .surname(surname)
                .password(password)
                .balance(balance)
                .role(Role.USER)
                .build();
        boolean isCreated = userService.create(user);
        if (isCreated){
            System.out.println("User muvofaqiyatli ro'yxatdan o'tdi");
        }else {
            System.out.println("Bu telefon raqam ro'yxatdan o'tdi");
        }
    }

    private void signIn() {
        System.out.println("Telefon raqamingizni kiriting: ");
        String phoneNumber = Context.stringScanner.nextLine();
        System.out.println("Parolingizni kiriting: ");
        String password = Context.stringScanner.nextLine();
        User user = userService.signIn(phoneNumber, password);
        if (Objects.isNull(user)){
            System.out.println("Telefon raqam yoki parol xato");
        }else {
            switch (user.getRole()){
                case USER -> {
                    UserUI userUI = new UserUI();
                    userUI.start(user);
                }
                case ADMIN -> {
                    AdminUI adminUI = new AdminUI();
                    adminUI.start();
                }
                case COOK -> {
                    CookUI cookUI = new CookUI();
                    cookUI.start();
                }
                case COURIER -> {
                    CourierUI courierUI = new CourierUI();
                    courierUI.start();
                }
            }
        }
    }
}
