package com.mycompany.hernandez_fmp2_machineproblem5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Hernandez_FMP2_MachineProblem5 {

    private static final Scanner scanner = new Scanner(System.in);

    private static final HashMap<Integer, Book> books = new HashMap<>();
    private static final HashSet<String> reservationKeys = new HashSet<>();

    // Encapsulated Book class
    static class Book {
        private int id;
        private String title;
        private int available;
        private int borrowed;
        private LinkedList<String> waitingPatrons;

        public Book(int id, String title, int copies) {
            this.id = id;
            this.title = title;
            this.available = copies;
            this.borrowed = 0;
            this.waitingPatrons = new LinkedList<>();
        }

        public void display() {
            System.out.println("\nBook ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Available: " + available);
            System.out.println("Borrowed: " + borrowed);
        }
    }

    public static void main(String[] args) {

        // Initialize the three required books
        books.put(1, new Book(1, "Java Basics", 1));
        books.put(2, new Book(2, "Algorithms", 0));
        books.put(3, new Book(3, "Database Systems", 2));

        int choice;

        do {
            showMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    reserveBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    findBook();
                    break;
                case 5:
                    showWaiting();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n===== LIBRARY SYSTEM =====");
        System.out.println("1. Borrow Book");
        System.out.println("2. Reserve Book");
        System.out.println("3. Return Book");
        System.out.println("4. Find Book");
        System.out.println("5. Show Waiting List");
        System.out.println("0. Exit");
    }

    // Safely read integer input
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    private static String readName() {
        while (true) {
            System.out.print("Enter patron name: ");
            String name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                return name;
            }

            System.out.println("Name cannot be empty.");
        }
    }

    private static Book selectBook() {
        int id = readInt("Enter book ID (1-3): ");

        Book book = books.get(id);

        if (book == null) {
            System.out.println("Book not found.");
        }

        return book;
    }

    // 1. BORROW BOOK
    private static void borrowBook() {
        Book book = selectBook();
        if (book == null) return;

        String name = readName();

        if (book.available > 0) {

            if (!book.waitingPatrons.isEmpty()) {
                System.out.println(
                    "Book has pending reservations."
                );
                System.out.println(
                    "Please wait for your turn."
                );
                return;
            }

            book.available--;
            book.borrowed++;

            System.out.println("Borrow successful!");
            System.out.println("Borrowed by: " + name);

        } else {
            System.out.println("No copies available.");
            System.out.println("Please reserve the book.");
        }
    }

    // 2. RESERVE BOOK
    private static void reserveBook() {
        Book book = selectBook();
        if (book == null) return;

        String name = readName();

        String key = book.id + ":" + name.toLowerCase();

        if (reservationKeys.contains(key)) {
            System.out.println("Duplicate reservation!");
            return;
        }

        if (book.available > 0 &&
            book.waitingPatrons.isEmpty()) {

            System.out.println(
                "A copy is available. Borrow instead."
            );
            return;
        }

        book.waitingPatrons.addLast(name);
        reservationKeys.add(key);

        System.out.println("Reservation successful!");
        System.out.println("Added to waiting list: " + name);
    }

    // 3. RETURN BOOK
    private static void returnBook() {
        Book book = selectBook();
        if (book == null) return;

        if (book.borrowed == 0) {
            System.out.println(
                "No borrowed copies to return."
            );
            return;
        }

        book.borrowed--;

        if (!book.waitingPatrons.isEmpty()) {

            String nextPatron =
                book.waitingPatrons.removeFirst();

            String key =
                book.id + ":" + nextPatron.toLowerCase();

            reservationKeys.remove(key);

            // Immediately assign the returned copy
            book.borrowed++;

            System.out.println(
                "Copy assigned to: " + nextPatron
            );

        } else {
            book.available++;

            System.out.println("Book returned successfully!");
            System.out.println(
                "Available: " + book.available
            );
        }

        System.out.println(
            "Borrowed: " + book.borrowed
        );
    }

    // 4. FIND BOOK
    private static void findBook() {
        Book book = selectBook();
        if (book == null) return;

        book.display();
    }

    // 5. SHOW WAITING LIST
    private static void showWaiting() {
        Book book = selectBook();
        if (book == null) return;

        System.out.println(
            "\nWaiting list for " + book.title + ":"
        );

        if (book.waitingPatrons.isEmpty()) {
            System.out.println("No waiting patrons.");
            return;
        }

        Iterator<String> iterator =
            book.waitingPatrons.iterator();

        int position = 1;

        while (iterator.hasNext()) {
            System.out.println(
                position + ". " + iterator.next()
            );
            position++;
        }
    }
}
