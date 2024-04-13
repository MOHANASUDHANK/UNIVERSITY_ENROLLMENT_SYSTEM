package mohan;

import java.sql.*;
import java.util.Scanner;

public class enrollmentSystem {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // Connect to the database
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Disconnect from the database
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Add a new student");
        System.out.println("2. Update student details");
        System.out.println("3. Delete a student");
        System.out.println("4. Enroll a student in a course");
        System.out.println("5. Unenroll a student from a course");
        System.out.println("6. Exit");
    }

    public static void main(String[] args) {
        try {
            // Connect to the database
            connect();

            int choice = 0;
            while (choice != 6) {
                displayMenu();
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        enrollStudent();
                        break;
                    case 5:
                        unenrollStudent();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a number from 1 to 6.");
                        break;
                }
            }

            // Disconnect from the database
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new student
    public static void addStudent() throws SQLException {
        System.out.println("Enter student ID:");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter student name:");
        String name = scanner.nextLine();
        System.out.println("Enter student email:");
        String email = scanner.nextLine();
        System.out.println("Enter student phone:");
        String phone = scanner.nextLine();

        String query = "INSERT INTO Students (StudentID, Name, Email, Phone) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentID);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone);
        preparedStatement.executeUpdate();
        System.out.println("Student added successfully.");
    }

    // Update student details
    public static void updateStudent() throws SQLException {
        System.out.println("Enter student ID to update:");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new email:");
        String newEmail = scanner.nextLine();
        System.out.println("Enter new phone:");
        String newPhone = scanner.nextLine();

        String query = "UPDATE Students SET Name = ?, Email = ?, Phone = ? WHERE StudentID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newName);
        preparedStatement.setString(2, newEmail);
        preparedStatement.setString(3, newPhone);
        preparedStatement.setInt(4, studentID);
        preparedStatement.executeUpdate();
        System.out.println("Student details updated successfully.");
    }

    // Delete a student
    public static void deleteStudent() throws SQLException {
        System.out.println("Enter student ID to delete:");
        int studentID = scanner.nextInt();

        String query = "DELETE FROM Students WHERE StudentID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentID);
        preparedStatement.executeUpdate();
        System.out.println("Student deleted successfully.");
    }

    // Enroll a student in a course
    public static void enrollStudent() throws SQLException {
        System.out.println("Enter student ID:");
        int studentID = scanner.nextInt();
        System.out.println("Enter course ID:");
        int courseID = scanner.nextInt();
        System.out.println("Enter faculty ID:");
        int facultyID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter grade:");
        String grade = scanner.nextLine();

        String query = "INSERT INTO Enrollments (StudentID, CourseID, FacultyID, Grade) VALUES (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentID);
        preparedStatement.setInt(2, courseID);
        preparedStatement.setInt(3, facultyID);
        preparedStatement.setString(4, grade);
        preparedStatement.executeUpdate();
        System.out.println("Student enrolled successfully.");
    }

    // Unenroll a student from a course
    public static void unenrollStudent() throws SQLException {
        System.out.println("Enter enrollment ID to unenroll:");
        int enrollmentID = scanner.nextInt();

        String query = "DELETE FROM Enrollments WHERE EnrollmentID = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, enrollmentID);
        preparedStatement.executeUpdate();
        System.out.println("Student unenrolled successfully.");
    }
}
