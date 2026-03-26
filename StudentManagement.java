import java.util.*;
import java.io.*;

// Base class
class Person {
    String name;
    int age;

    void getPersonDetails(Scanner sc) {
        System.out.print("Enter name: ");
        name = sc.nextLine();
        System.out.print("Enter age: ");
        age = sc.nextInt();
        sc.nextLine(); // clear buffer
    }
}

// Derived class
class Student extends Person {
    int id;
    double marks;
    String grade;

    void getStudentDetails(Scanner sc) {
        System.out.print("Enter ID: ");
        id = sc.nextInt();
        sc.nextLine();

        getPersonDetails(sc);

        System.out.print("Enter marks: ");
        marks = sc.nextDouble();
        sc.nextLine();

        calculateGrade();
    }

    void calculateGrade() {
        if (marks >= 80)
            grade = "A";
        else if (marks >= 60)
            grade = "B";
        else if (marks >= 40)
            grade = "C";
        else
            grade = "Fail";
    }

    void display() {
        System.out.println("--------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
    }
}

// Manager class
class StudentManager {
    ArrayList<Student> list = new ArrayList<>();

    void addStudent(Scanner sc) {
        Student s = new Student();
        s.getStudentDetails(sc);
        list.add(s);
        System.out.println("Student added successfully!\n");
    }

    void displayAll() {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Student s : list) {
            s.display();
        }
    }

    void searchStudent(int id) {
        boolean found = false;

        for (Student s : list) {
            if (s.id == id) {
                s.display();
                found = true;
                break;
            }
        }

        if (!found)
            System.out.println("Student not found.");
    }

    void deleteStudent(int id) {
        Iterator<Student> it = list.iterator();

        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == id) {
                it.remove();
                System.out.println("Student deleted.");
                return;
            }
        }

        System.out.println("Student not found.");
    }

    void saveToFile() {
        try {
            FileWriter fw = new FileWriter("students.txt");

            for (Student s : list) {
                fw.write(s.id + "," + s.name + "," + s.age + "," + s.marks + "," + s.grade + "\n");
            }

            fw.close();
            System.out.println("Data saved to file.");

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    void loadFromFile() {
        try {
            File file = new File("students.txt");

            if (!file.exists()) return;

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");

                Student s = new Student();
                s.id = Integer.parseInt(data[0]);
                s.name = data[1];
                s.age = Integer.parseInt(data[2]);
                s.marks = Double.parseDouble(data[3]);
                s.grade = data[4];

                list.add(s);
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}

// Main class
public class StudentManagement {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager sm = new StudentManager();

        sm.loadFromFile(); // load previous data

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Save to File");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        sm.addStudent(sc);
                        break;

                    case 2:
                        sm.displayAll();
                        break;

                    case 3:
                        System.out.print("Enter ID to search: ");
                        int id = sc.nextInt();
                        sm.searchStudent(id);
                        break;

                    case 4:
                        System.out.print("Enter ID to delete: ");
                        int did = sc.nextInt();
                        sm.deleteStudent(did);
                        break;

                    case 5:
                        sm.saveToFile();
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                sc.nextLine(); // clear buffer
                choice = 0;
            }

        } while (choice != 6);

        sc.close();
    }
}