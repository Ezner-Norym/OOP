package com.mycompany.fmp_machineproblem_2;

import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    private int accountNumber;
    private double balance;

    public Account(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive."
            );
        }

        balance += amount;
    }

    public void withdraw(double amount)
            throws InsufficientFundsException {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive."
            );
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds."
            );
        }

        balance -= amount;
    }
}

public class FMP_MachineProblem_2 {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Account[] accounts = {
        new Account(101, 1000.00),
        new Account(102, 500.00)
    };

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== ATM BANKING SYSTEM =====");
            System.out.println("1. Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(
                    scanner.nextLine().trim()
                );

                switch (choice) {
                    case 1:
                        checkBalance();
                        break;

                    case 2:
                        depositMoney();
                        break;

                    case 3:
                        withdrawMoney();
                        break;

                    case 0:
                        System.out.println(
                            "\nThank you for using the ATM!"
                        );
                        break;

                    default:
                        System.out.println(
                            "Error: Invalid menu choice."
                        );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                    "Error: Enter a valid number."
                );
                choice = -1;
            }

        } while (choice != 0);

        scanner.close();
    }

    private static Account findAccount(int accountNumber) {

        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }

    private static Account selectAccount() {

        System.out.print("Enter account number: ");
        String input = scanner.nextLine().trim();

        try {
            int accountNumber = Integer.parseInt(input);

            Account account = findAccount(accountNumber);

            if (account == null) {
                System.out.println("Error: Account not found.");
            }

            return account;

        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Enter a valid number."
            );
            return null;
        }
    }

    private static void checkBalance() {

        Account account = selectAccount();

        if (account == null) {
            return;
        }

        System.out.printf(
            "Balance: %.2f%n",
            account.getBalance()
        );
    }

    private static void depositMoney() {

        Account account = selectAccount();

        if (account == null) {
            return;
        }

        try {
            System.out.print("Enter deposit amount: ");
            double amount = Double.parseDouble(
                scanner.nextLine().trim()
            );

            account.deposit(amount);

            System.out.println("Deposit successful!");
            System.out.printf(
                "New balance: %.2f%n",
                account.getBalance()
            );

        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Enter a valid number."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }

    private static void withdrawMoney() {

        Account account = selectAccount();

        if (account == null) {
            return;
        }

        try {
            System.out.print("Enter withdrawal amount: ");
            double amount = Double.parseDouble(
                scanner.nextLine().trim()
            );

            account.withdraw(amount);

            System.out.println("Withdrawal successful!");
            System.out.printf(
                "New balance: %.2f%n",
                account.getBalance()
            );

        } catch (NumberFormatException e) {
            System.out.println(
                "Error: Enter a valid number."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Error: " + e.getMessage()
            );

        } catch (InsufficientFundsException e) {
            System.out.println(
                "Error: " + e.getMessage()
            );
        }
    }
}
