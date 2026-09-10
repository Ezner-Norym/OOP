import java.util.Scanner;

public class Activity1_6 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter account holder name: ");
        String holderName = s.nextLine();

        System.out.print("Enter account number: ");
        String accountNumber = s.nextLine();

        System.out.print("Enter account type: ");
        String accountType = s.nextLine();

        System.out.print("Enter current balance: ");
        double balance = s.nextDouble();

        System.out.println("Holder: " + holderName + "; Account Number: " + accountNumber +
                "; Type: " + accountType + "; Balance: PHP " + balance);
    }
}
