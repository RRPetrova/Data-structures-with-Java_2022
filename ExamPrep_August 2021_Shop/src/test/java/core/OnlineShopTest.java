package core;

import model.HardwareOrder;
import model.Order;
import org.junit.Test;
import shared.Shop;

import static org.junit.Assert.*;

public class OnlineShopTest {

    @Test
    public void testContainsShouldReturnTrue() {
        Shop shop = new OnlineShop();
        for (int i = 0; i < 20; i++) {
            shop.add(new HardwareOrder(i, "hardware_order"));
        }
        Boolean isPresent = shop.contains(new HardwareOrder(13, "hardware_order"));
        assertNotNull(isPresent);
        assertTrue(isPresent);
    }

    @Test
    public void testIndexOfShouldReturnCorrectValue() {
        Shop shop = new OnlineShop();
        for (int i = 0; i < 20; i++) {
            shop.add(new HardwareOrder(i, "hardware_order"));
        }
        int index = shop.indexOf(new HardwareOrder(13, "hardware_order"));
        assertEquals(13, index);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetByIndexShouldThrow() {
        Shop shop = new OnlineShop();
        for (int i = 0; i < 20; i++) {
            shop.add(new HardwareOrder(i, "hardware_order"));
        }
        shop.get(20);
    }

    @Test
    public void testGetByIndex() {
        Shop shop = new OnlineShop();
        for (int i = 0; i < 20; i++) {
            shop.add(new HardwareOrder(i, "hardware_order"));
        }
        Order order = shop.get(10);
        assertNotNull(order);
        assertEquals(10, order.getId());
    }

    @Test
    public void testAddMultipleElements() {
        Shop shop = new OnlineShop();
        assertEquals(0, shop.size());

        for (int i = 0; i < 100; i++) {
            shop.add(new HardwareOrder(i, "hardware_order"));
        }

        assertEquals(100, shop.size());
    }

    @Test
    public void testAddSingleElement() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");

        assertEquals(0, shop.size());
        shop.add(order);

        assertEquals(1, shop.size());
        assertTrue(shop.contains(order));
    }

    @Test
    public void testReverse() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");
        Order order1 = new HardwareOrder(1, "hardware_order");
        Order order2 = new HardwareOrder(12, "hardware_order");

        assertEquals(0, shop.size());
        shop.add(order);
        shop.add(order1);
        shop.add(order2);

        assertEquals(3, shop.size());
        shop.reverse();
        assertEquals(shop.get(0).getId(), 12);
        assertEquals(shop.get(1).getId(), 1);
        assertEquals(shop.get(2).getId(), 13);
    }


    @Test
    public void testRemove() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");
        Order order1 = new HardwareOrder(1, "hardware_order");
        Order order2 = new HardwareOrder(12, "hardware_order");
        Order order3 = new HardwareOrder(22, "hardware_order");

        assertEquals(0, shop.size());
        shop.add(order);
        shop.add(order1);
        shop.add(order2);
        shop.add(order3);

        assertEquals(4, shop.size());
        assertTrue( shop.remove(1));

        assertEquals(shop.get(0).getId(), 13);
        assertEquals(shop.get(1).getId(), 12);
        assertEquals(shop.get(2).getId(), 22);
        assertEquals(3, shop.size());
    }

    @Test
    public void testRemoveInvalidId() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");
        Order order1 = new HardwareOrder(1, "hardware_order");
        Order order2 = new HardwareOrder(12, "hardware_order");
        Order order3 = new HardwareOrder(22, "hardware_order");

        assertEquals(0, shop.size());
        shop.add(order);
        shop.add(order1);
        shop.add(order2);


        assertEquals(3, shop.size());

        assertFalse(shop.remove(order3));
//        assertEquals(shop.get(0).getId(), 13);
//        assertEquals(shop.get(1).getId(), 1);
//        assertEquals(shop.get(2).getId(), 12);
//        assertEquals(shop.get(3).getId(), 22);
//        assertEquals(4, shop.size());
    }

    @Test
    public void testInsert1() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");
        Order order1 = new HardwareOrder(22, "hardware_order");
        Order order3 = new HardwareOrder(25, "hardware_order");

        assertEquals(0, shop.size());
        shop.add(order);
        shop.add(order1);
        shop.add(order3);

        assertEquals(3, shop.size());
        Order order2 = new HardwareOrder(255, "hardware_order");
        shop.insert(1, order2);

        assertEquals(shop.get(0).getId(), 13);
        assertEquals(shop.get(1).getId(), 255);
        assertEquals(shop.get(2).getId(), 22);
        assertEquals(shop.get(3).getId(), 25);
        assertEquals(4, shop.size());

    }

    @Test
    public void testInsert2() {
        Shop shop = new OnlineShop();

        Order order = new HardwareOrder(13, "hardware_order");

        assertEquals(0, shop.size());
        shop.insert(0, order);

        assertEquals(1, shop.size());
        assertTrue(shop.contains(order));
        assertEquals(shop.get(0).getId(), 13);
    }
}