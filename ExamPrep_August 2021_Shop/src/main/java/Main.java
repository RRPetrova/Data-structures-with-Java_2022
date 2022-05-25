import core.OnlineShop;
import model.HardwareOrder;
import model.Order;
import shared.Shop;

public class Main {
    public static void main(String[] args) {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");
        Order order1 = new HardwareOrder(1, "hardware_order");
        Order order2= new HardwareOrder(12, "hardware_order");

        shop.add(order);
        shop.add(order1);
        shop.add(order2);
        for (int i = 0; i < shop.size(); i++) {
            System.out.println(shop.get(i).getId());
        }

        shop.reverse();
        for (int i = 0; i < shop.size(); i++) {
            System.out.println(shop.get(i).getId());
        }
    }
}