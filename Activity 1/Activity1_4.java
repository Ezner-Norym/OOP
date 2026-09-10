import java.util.Scanner;

public class Activity1_4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter brand: ");
        String brand = s.nextLine();

        System.out.print("Enter model: ");
        String model = s.nextLine();

        System.out.print("Enter RAM (GB): ");
        int ram = s.nextInt();

        System.out.print("Enter storage (GB): ");
        int storage = s.nextInt();

        System.out.print("Enter price: ");
        double price = s.nextDouble();

        System.out.println("Brand: " + brand + "; Model: " + model +
                "; RAM: " + ram + " GB; Storage: " + storage +
                " GB; Price: PHP " + price);
    }
}
