package com.mycompany.hernandez_fmp2_machineproblem4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hernandez_FMP2_MachineProblem4 {

    private static final Scanner scanner = new Scanner(System.in);

    private static final HashMap<Integer, Product> products =
            new HashMap<>();

    static class Product {
        private int id;
        private String name;
        private double price;
        private int stock;

        public Product(int id, String name, double price, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public void addStock(int quantity) {
            stock += quantity;
        }

        public void sell(int quantity) {
            stock -= quantity;
        }

        public void display() {
            System.out.printf(
                "ID: %d | Name: %s | Price: %.2f | Stock: %d%n",
                id, name, price, stock
            );
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            showMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    findProduct();
                    break;
                case 3:
                    restockProduct();
                    break;
                case 4:
                    sellProduct();
                    break;
                case 5:
                    listProducts();
                    break;
                case 6:
                    sortByPrice();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu choice.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n===== INVENTORY SYSTEM =====");
        System.out.println("1. Add Product");
        System.out.println("2. Find Product");
        System.out.println("3. Restock Product");
        System.out.println("4. Sell Product");
        System.out.println("5. List All Products");
        System.out.println("6. Sort by Price");
        System.out.println("0. Exit");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                double value =
                    Double.parseDouble(scanner.nextLine().trim());

                if (Double.isNaN(value) ||
                    Double.isInfinite(value)) {
                    System.out.println("Enter a finite number.");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    private static String readName() {
        while (true) {
            System.out.print("Enter product name: ");
            String name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                return name;
            }

            System.out.println("Product name cannot be empty.");
        }
    }

    private static void addProduct() {
        int id = readInt("Enter product ID: ");

        if (id <= 0) {
            System.out.println("ID must be positive.");
            return;
        }

        if (products.containsKey(id)) {
            System.out.println("Rejected: Duplicate product ID.");
            return;
        }

        String name = readName();
        double price = readDouble("Enter price: ");
        int stock = readInt("Enter initial stock: ");

        if (price < 0 || stock < 0) {
            System.out.println(
                "Rejected: Price and stock cannot be negative."
            );
            return;
        }

        Product product = new Product(id, name, price, stock);
        products.put(id, product);

        System.out.println("Product added successfully!");
    }

    private static void findProduct() {
        int id = readInt("Enter product ID: ");

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Product found:");
        product.display();
    }

    private static void restockProduct() {
        int id = readInt("Enter product ID: ");

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter restock quantity: ");

        if (quantity <= 0) {
            System.out.println(
                "Rejected: Quantity must be positive."
            );
            return;
        }

        if (quantity > Integer.MAX_VALUE - product.getStock()) {
            System.out.println(
                "Rejected: Stock limit exceeded."
            );
            return;
        }

        product.addStock(quantity);

        System.out.println("Restock successful!");
        product.display();
    }

    private static void sellProduct() {
        int id = readInt("Enter product ID: ");

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity to sell: ");

        if (quantity <= 0) {
            System.out.println(
                "Rejected: Quantity must be positive."
            );
            return;
        }

        if (quantity > product.getStock()) {
            System.out.println(
                "Rejected: Insufficient stock."
            );
            System.out.println(
                "Remaining stock: " + product.getStock()
            );
            return;
        }

        product.sell(quantity);

        System.out.println("Sale successful!");
        product.display();
    }

    private static void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n===== PRODUCT LIST =====");

        for (Product product : products.values()) {
            product.display();
        }
    }

    private static void sortByPrice() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        ArrayList<Product> sortedProducts =
                new ArrayList<>(products.values());

        sortedProducts.sort(
            Comparator.comparingDouble(Product::getPrice)
                      .thenComparingInt(Product::getId)
        );

        System.out.println("\n===== SORTED BY PRICE =====");

        for (Product product : sortedProducts) {
            product.display();
        }
    }
}
