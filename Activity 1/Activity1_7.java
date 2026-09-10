import java.util.Scanner;

public class Activity1_7 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter quiz grade: ");
        double quiz = s.nextDouble();

        System.out.print("Enter project grade: ");
        double project = s.nextDouble();

        System.out.print("Enter exam grade: ");
        double exam = s.nextDouble();

        double finalGrade = (quiz * 0.30) + (project * 0.30) + (exam * 0.40);

        System.out.println("Final Grade: " + finalGrade);
    }    
}
