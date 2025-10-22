package ShoppingCartAssignment;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public double calculateAllItemsCost() {
        double total = 0.0;
        for (Item item : items) {
            total += item.totalCost(); // use totalCost (price * quantity)
        }
        return total;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
