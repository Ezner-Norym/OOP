import java.util.Scanner;

public class Activity1_1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String Full_Name;
        String Student_Number;
        String Program;
        int Year_Level;
        double GPA;

        System.out.print("Enter Full Name: ");
        Full_Name = s.nextLine();

        System.out.print("Enter Student Number: ");
        Student_Number = s.nextLine();

        System.out.print("Enter Program: ");
        Program = s.nextLine();

        System.out.print("Enter Year Level: ");
        Year_Level = s.nextInt();

        System.out.print("Enter GPA: ");
        GPA = s.nextDouble();

        System.out.println("Full Name: " + Full_Name);
        System.out.println("Student Number: " + Student_Number);
        System.out.println("Program: " + Program);
        System.out.println("Year Level: " + Year_Level);
        System.out.println("GPA: " + GPA);
    }
}
