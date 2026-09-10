import java.util.Scanner;

public class Activity1_2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter food item: ");
        String foodItem = s.nextLine();

        System.out.print("Enter category: ");
        String category = s.nextLine();

        System.out.print("Enter price: ");
        double price = s.nextDouble();

        System.out.print("Enter serving size: ");
        int servingSize = s.nextInt();

        System.out.println("Item: " + foodItem + "; Category: " + category +
        "; Price: PHP " + price + "; Serving Size: " + servingSize);
    }
}
