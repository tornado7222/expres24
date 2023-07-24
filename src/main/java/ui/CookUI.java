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

public class CookUI {
    OrderService orderService = OrderService.getInstance();
    RestaurantService restaurantService = RestaurantService.getInstance();
    public void start(User cook) {
        List<Order> orderList = orderService.findByUserId(cook.getId());
        List<Order> orders = new ArrayList<>();
        boolean isExited = false;
        while (!isExited) {
            orders = orderList.stream()
                    .filter(order -> order.getStatus().equals(OrderStatus.IS_COOK))
                    .toList();
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                Restaurant restaurant = restaurantService.findById(cook.getRestaurantId());
                System.out.println(restaurant.getName() + " restaurantidagi Zakaslar ");
                System.out.println(i + " - Zakas");
                for (Product product : order.getProducts()) {
                    System.out.println(i + ". " + product.getName());
                }
                System.out.println(" Zakasni tayorlash uchun tanlang ");
                int orderIndex = Context.intScanner.nextInt();
                if (orderIndex >= 0 && orderIndex < orders.size()) {
                    Order order1 = orders.get(orderIndex);
                    order1.setStatus(OrderStatus.IS_BEGIN_COOKED);
                    System.out.println("Zakas ni tugatish uchun  $ -ni bosing");
                    String command = Context.stringScanner.nextLine();
                    if (command.equals("$")){
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
