package com.mycompany.problem10;

import java.util.Scanner;

public class Problem10 {

    static final int MAX_STUDENTS = 20;
    static final int MAX_COURSES = 20;
    static final int MAX_ENROLLMENTS = 100;

    static Student[] students = new Student[MAX_STUDENTS];
    static Course[] courses = new Course[MAX_COURSES];
    static Enrollment[] enrollments = new Enrollment[MAX_ENROLLMENTS];

    static int studentCount = 0;
    static int courseCount = 0;
    static int enrollmentCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("Choose an option: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                addStudent(input);
            } else if (choice == 2) {
                addCourse(input);
            } else if (choice == 3) {
                enrollStudent(input);
            } else if (choice == 4) {
                updateGrade(input);
            } else if (choice == 5) {
                studentReport(input);
            } else if (choice == 6) {
                courseRoster(input);
            } else if (choice == 7) {
                systemSummary();
            } else if (choice == 0) {
                System.out.println("Program ended.");
            } else {
                System.out.println("Invalid option.");
            }
        } while (choice != 0);

        input.close();
    }

    public static void displayMenu() {
        System.out.println("\n=== UNIVERSITY ENROLLMENT SYSTEM ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Enroll Student");
        System.out.println("4. Update Grade");
        System.out.println("5. Student Report");
        System.out.println("6. Course Roster");
        System.out.println("7. System Summary");
        System.out.println("0. Exit");
    }

    public static void addStudent(Scanner input) {
        if (studentCount == MAX_STUDENTS) {
            System.out.println("Student list is full.");
            return;
        }

        System.out.print("Enter student ID: ");
        String id = input.nextLine();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        System.out.print("Enter full name: ");
        String name = input.nextLine();

        students[studentCount] = new Student(id, name);
        studentCount++;
        System.out.println("Student added successfully.");
    }

    public static void addCourse(Scanner input) {
        if (courseCount == MAX_COURSES) {
            System.out.println("Course list is full.");
            return;
        }

        System.out.print("Enter course code: ");
        String code = input.nextLine();

        if (findCourse(code) != null) {
            System.out.println("Course code already exists.");
            return;
        }

        System.out.print("Enter course title: ");
        String title = input.nextLine();

        System.out.print("Enter course capacity: ");
        int capacity = input.nextInt();
        input.nextLine();

        if (capacity < 1) {
            System.out.println("Capacity must be at least 1.");
            return;
        }

        courses[courseCount] = new Course(code, title, capacity);
        courseCount++;
        System.out.println("Course added successfully.");
    }

    public static void enrollStudent(Scanner input) {
        if (enrollmentCount == MAX_ENROLLMENTS) {
            System.out.println("Enrollment list is full.");
            return;
        }

        System.out.print("Enter student ID: ");
        String studentId = input.nextLine();
        Student student = findStudent(studentId);

        System.out.print("Enter course code: ");
        String courseCode = input.nextLine();
        Course course = findCourse(courseCode);

        if (student == null || course == null) {
            System.out.println("Enrollment rejected: Student or course does not exist.");
        } else if (isAlreadyEnrolled(student, course)) {
            System.out.println("Enrollment rejected: Student is already enrolled in this course.");
        } else if (getCourseEnrollmentCount(course) >= course.getCapacity()) {
            System.out.println("Enrollment rejected: Course is already at capacity.");
        } else {
            enrollments[enrollmentCount] = new Enrollment(student, course, -1);
            enrollmentCount++;
            System.out.println("Enrollment successful.");
        }
    }

    public static void updateGrade(Scanner input) {
        System.out.print("Enter student ID: ");
        String studentId = input.nextLine();

        System.out.print("Enter course code: ");
        String courseCode = input.nextLine();

        Enrollment enrollment = findEnrollment(studentId, courseCode);

        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }

        System.out.print("Enter grade (0 to 100): ");
        double grade = input.nextDouble();
        input.nextLine();

        if (grade < 0 || grade > 100) {
            System.out.println("Invalid grade. Grade must be from 0 to 100.");
            return;
        }

        enrollment.setGrade(grade);
        System.out.println("Grade updated successfully.");
    }

    public static void studentReport(Scanner input) {
        System.out.print("Enter student ID: ");
        String studentId = input.nextLine();
        Student student = findStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\n=== STUDENT REPORT ===");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFullName());

        double totalGrades = 0;
        int gradedCount = 0;
        boolean hasCourses = false;

        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getStudent() == student) {
                hasCourses = true;
                Enrollment enrollment = enrollments[i];
                System.out.print(enrollment.getCourse().getCourseCode() + " - ");
                System.out.print(enrollment.getCourse().getTitle() + " | ");

                if (enrollment.getGrade() == -1) {
                    System.out.println("NOT YET GRADED");
                } else {
                    System.out.printf("Grade: %.2f | %s%n", enrollment.getGrade(),
                            enrollment.getStatus());
                    totalGrades += enrollment.getGrade();
                    gradedCount++;
                }
            }
        }

        if (!hasCourses) {
            System.out.println("No enrolled courses.");
        }

        if (gradedCount > 0) {
            System.out.printf("Average Grade: %.2f%n", totalGrades / gradedCount);
        } else {
            System.out.println("Average Grade: No graded courses yet.");
        }
    }

    public static void courseRoster(Scanner input) {
        System.out.print("Enter course code: ");
        String courseCode = input.nextLine();
        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("\n=== COURSE ROSTER ===");
        System.out.println(course.getCourseCode() + " - " + course.getTitle());
        System.out.println("Enrolled: " + getCourseEnrollmentCount(course)
                + "/" + course.getCapacity());

        boolean hasStudents = false;
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getCourse() == course) {
                Student student = enrollments[i].getStudent();
                System.out.println(student.getStudentId() + " - " + student.getFullName());
                hasStudents = true;
            }
        }

        if (!hasStudents) {
            System.out.println("No enrolled students.");
        }
    }

    public static void systemSummary() {
        int gradedEnrollmentCount = 0;

        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getGrade() != -1) {
                gradedEnrollmentCount++;
            }
        }

        System.out.println("\n=== SYSTEM SUMMARY ===");
        System.out.println("Total Students: " + studentCount);
        System.out.println("Total Courses: " + courseCount);
        System.out.println("Total Enrollments: " + enrollmentCount);
        System.out.println("Graded Enrollments: " + gradedEnrollmentCount);
    }

    public static Student findStudent(String studentId) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equalsIgnoreCase(studentId)) {
                return students[i];
            }
        }
        return null;
    }

    public static Course findCourse(String courseCode) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseCode().equalsIgnoreCase(courseCode)) {
                return courses[i];
            }
        }
        return null;
    }

    public static Enrollment findEnrollment(String studentId, String courseCode) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getStudent().getStudentId().equalsIgnoreCase(studentId)
                    && enrollments[i].getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                return enrollments[i];
            }
        }
        return null;
    }

    public static boolean isAlreadyEnrolled(Student student, Course course) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getStudent() == student && enrollments[i].getCourse() == course) {
                return true;
            }
        }
        return false;
    }

    public static int getCourseEnrollmentCount(Course course) {
        int count = 0;
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollments[i].getCourse() == course) {
                count++;
            }
        }
        return count;
    }
}

class Student {
    private String studentId;
    private String fullName;

    public Student(String studentId, String fullName) {
        this.studentId = studentId;
        this.fullName = fullName;
    }

    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
}

class Course {
    private String courseCode;
    private String title;
    private int capacity;

    public Course(String courseCode, String title, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.capacity = capacity;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCapacity() { return capacity; }
}

class Enrollment {
    private Student student;
    private Course course;
    private double grade;

    public Enrollment(Student student, Course course, double grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getGrade() { return grade; }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getStatus() {
        if (grade == -1) {
            return "NOT YET GRADED";
        } else if (grade >= 75) {
            return "PASSED";
        } else {
            return "FAILED";
        }
    }
}
