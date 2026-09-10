import java.util.Scanner;

public class Activity1_5 {
     public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter movie title: ");
        String title = s.nextLine();

        System.out.print("Enter director: ");
        String director = s.nextLine();

        System.out.print("Enter release year: ");
        int releaseYear = s.nextInt();

        System.out.print("Enter runtime (minutes): ");
        int runtime = s.nextInt();

        System.out.print("Enter rating: ");
        double rating = s.nextDouble();

        System.out.println("Title: " + title + "; Director: " + director +
                "; Year: " + releaseYear + "; Runtime: " + runtime +
                " minutes; Rating: " + rating);
    }
}
