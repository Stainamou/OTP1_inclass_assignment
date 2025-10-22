package ShoppingCartAssignment;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select language / Valitse kieli / Välj språk / 言語を選択してください:");
        System.out.println("1. English");
        System.out.println("2. Suomi");
        System.out.println("3. Svenska");
        System.out.println("4. 日本語");
        System.out.println("> ");
        String choice = scanner.nextLine().trim();

        Locale locale;
        switch (choice) {
            case "2": locale = new Locale("fi", "FI"); break;
            case "3": locale = new Locale("sv", "SE"); break;
            case "4": locale = new Locale("ja", "JP"); break;
            default: locale = new Locale("en", "US"); break;
        }

        ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", locale);

        int itemCount;
        while (true) {
            System.out.println(rb.getString("prompt.itemCount") + " ");
            try {
                itemCount = Integer.parseInt(scanner.nextLine().trim());
                if (itemCount >= 0) break;
            } catch (NumberFormatException ignored) { }
            System.out.println(rb.getString("error.invalidNumber"));
        }

        ShoppingCart cart = new ShoppingCart();

        for (int i = 1; i <= itemCount; i++) {
            double price;
            while (true) {
                System.out.println(MessageFormat.format(rb.getString("prompt.price"), i) + " ");
                try {
                    price = Double.parseDouble(scanner.nextLine().trim());
                    if (price >= 0) break;
                } catch (NumberFormatException ignored) { }
                System.out.println(rb.getString("error.invalidNumber"));
            }

            int quantity;
            while (true) {
                System.out.println(MessageFormat.format(rb.getString("prompt.quantity"), i) + " ");
                try {
                    quantity = Integer.parseInt(scanner.nextLine().trim());
                    if (quantity >= 0) break;
                } catch (NumberFormatException ignored) { }
                System.out.println(rb.getString("error.invalidNumber"));
            }

            Item item = new Item(price, quantity);
            cart.addItem(item);

            System.out.println(MessageFormat.format(rb.getString("output.itemTotal"), i, item.totalCost()));
        }

        double total  = cart.calculateAllItemsCost();
        System.out.println(MessageFormat.format(rb.getString("output.total"), total));

        scanner.close();
    }
}
