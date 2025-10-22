import ShoppingCartAssignment.Item;
import ShoppingCartAssignment.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ShoppingCartTest {

    @Test
    void testItemTotalCost() {
        Item item = new Item(2.5, 4); // price 2.5, quantity 4 -> total 10.0
        Assertions.assertEquals(10.0, item.totalCost(), 1e-6);
    }

    @Test
    void testCartTotalCost() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new Item(1.0, 3));   // 3.0
        cart.addItem(new Item(2.5, 2));   // 5.0
        cart.addItem(new Item(0.99, 1));  // 0.99
        double expected = 3.0 + 5.0 + 0.99;
        Assertions.assertEquals(expected, cart.calculateAllItemsCost(), 1e-6);
    }

    @Test
    void testEmptyCartIsZero() {
        ShoppingCart cart = new ShoppingCart();
        Assertions.assertEquals(0.0, cart.calculateAllItemsCost(), 1e-6);
    }
}
