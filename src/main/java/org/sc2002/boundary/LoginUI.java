import java.util.List;
import java.util.Scanner;

public class LoginUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Login Page");

        // Get user input for user ID and password
        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        UserController userController = new UserController();

        User authenticatedUser = userController.authenticateUser(userId, password);

        if (authenticatedUser != null) {
            System.out.println("Login successful. Welcome, " + authenticatedUser.getUserId() + "!");
            System.out.println("Faculty: " + authenticatedUser.getFaculty());
            System.out.println("User Role: " + userController.getUserRole(authenticatedUser));
        } else {
            System.out.println("Login failed. Invalid user ID or password.");
        }

        scanner.close();
    }
}
