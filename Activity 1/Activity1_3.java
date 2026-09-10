import java.util.Scanner;

public class Activity1_3 {
     public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter guest name: ");
        String guestName = s.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = s.nextInt();

        System.out.print("Enter number of nights: ");
        int nights = s.nextInt();

        System.out.print("Enter room rate: ");
        double roomRate = s.nextDouble();

        System.out.println("Guest: " + guestName + "; Room: " + roomNumber +
            "; Nights: " + nights + "; Room Rate: PHP " + roomRate);
    }
}
