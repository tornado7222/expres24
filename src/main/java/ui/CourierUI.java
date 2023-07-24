package ui;

import context.Context;
import order.Order;
import order.OrderService;
import order.OrderStatus;
import product.Product;
import restaurant.Restaurant;
import restaurant.RestaurantService;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class CourierUI {
    OrderService orderService = OrderService.getInstance();
    RestaurantService restaurantService = RestaurantService.getInstance();
    public void start(User courier){
        List<Order> orderList = orderService.findByUserId(courier.getId());
        List<Order> orders = new ArrayList<>();
        boolean isExited = false;
        while (!isExited) {
            orders = orderList.stream()
                    .filter(order -> order.getStatus().equals(OrderStatus.IS_COURIER))
                    .toList();
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                Restaurant restaurant = restaurantService.findById(courier.getRestaurantId());
                System.out.println(restaurant.getName() + " restaurantidagi Zakaslar ");
                System.out.println(i + " - Zakas " + "Location " + order.getLocation() + "Buyurtmachini telefon raqami " + order.getCreatBy().getPhoneNumber());
                System.out.println(" Zakasni yetkazish uchun tanlang: ");
                int orderIndex = Context.intScanner.nextInt();
                if (orderIndex >= 0 && orderIndex < orders.size()) {
                    Order order1 = orders.get(orderIndex);
                    order1.setStatus(OrderStatus.IS_BEGIN_DELIVERED);
                    System.out.println("Yetib bordingizm? y/n");
                    String command = Context.stringScanner.nextLine();
                    String lowerCase = command.toLowerCase();
                    if (lowerCase.equals("y")){
                        order1.setStatus(OrderStatus.READY_DELIVER);
                        isExited = true;
                    }
                } else {
                    System.out.println("Noto'g'ri bunday zakas yoq");
                }

            }
        }
    }
}
