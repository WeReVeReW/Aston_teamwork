import java.util.*;

public class Main {
    public static void main(String[] args) {
        BookBuilder bookBuilder = new BookBuilder();
        Book book = bookBuilder
                .setId(1)
                .setName("Java for Beginners")
                .setPrice(25.99)
                .setGenre("Programming")
                .build();

        PhoneBuilder phoneBuilder = new PhoneBuilder();
        Phone phone = phoneBuilder
                .setId(2)
                .setName("iPhone 14")
                .setPrice(999.99)
                .setColor("Black")
                .build();

        DiscBuilder discBuilder = new DiscBuilder();
        Disc disc = discBuilder
                .setId(3)
                .setName("The Best of 2023")
                .setPrice(15.99)
                .setYear(2023)
                .build();

        DoshirakBuilder doshirakBuilder = new DoshirakBuilder();
        Doshirak doshirak = doshirakBuilder
                .setId(4)
                .setName("Doshirak Spicy")
                .setPrice(2.99)
                .setTaste(5)
                .build();

        List<Product> products = new ArrayList<>();
        products.add(book);
        products.add(phone);
        products.add(disc);
        products.add(doshirak);

        Collections.sort(products);

        for (Product p : products) {
            System.out.println(p);
        }

        products.sort(Comparator.comparingDouble(Product::getPrice));

        System.out.println("\nProducts sorted by price:");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
