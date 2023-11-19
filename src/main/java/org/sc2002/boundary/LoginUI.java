package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;

import java.util.List;
import java.util.Scanner;

public class LoginUI implements UI{

    private final UserController userController;

    public LoginUI(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void body() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[36m\n   _     _    ______    __        __        ______    __    \u001B[0m");
        System.out.println("\u001B[36m  | |   | |  |  ____|  |  |      |  |      |  __  |  |  |   \u001B[0m");
        System.out.println("\u001B[36m  | |___| |  |  |___   |  |      |  |      | |  | |  |  |   \u001B[0m");
        System.out.println("\u001B[36m  |  ___  |  |  ____|  |  |      |  |      | |  | |  |__|   \u001B[0m");
        System.out.println("\u001B[36m  | |   | |  |  |___   |  |___   |  |___   | |__| |   __    \u001B[0m" );
        System.out.println("\u001B[36m  |_|   |_|  |______|  |______|  |______|  |______|  |__|   \u001B[0m");

        System.out.println("\u001B[36m\n =================================================================\u001B[0m");
        System.out.println("\u001B[36m     Welcome to Camp Application and Management System (CAMs)    \u001B[0m");
        System.out.println("\u001B[36m =================================================================\u001B[0m");

        System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
        System.out.println("1. Login");
        System.out.println("2. Forget UserID");
        System.out.println("3. Exit");

        System.out.print("\nYour choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                changePassword();
                break;
            case 3:
                System.out.println("\nExiting. Goodbye!");
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }

        scanner.close();
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        // Get user input for user ID and password
        System.out.print("\nEnter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User authenticatedUser = userController.authenticateUser(userId, password);

        if (authenticatedUser != null) {
            System.out.println("\u001B[33m \nLogin successful. Welcome " + authenticatedUser.getName().toUpperCase() + "!\u001B[0m");
            System.out.println("\u001B[34mFaculty:\u001B[0m " + authenticatedUser.getFaculty());
            //System.out.println("\u001B[34mUser Role:\u001B[0m " + userController.getUserRole(authenticatedUser));
            String userRole = userController.getUserRole(authenticatedUser);
        System.out.println("\u001B[34mUser Role:\u001B[0m " + userRole);

        switch (userRole) {
            case "Staff Member":
                displayStaffFeatures();
                break;
            case "Student":
                displayStudentFeatures();
                break;
            default:
                System.out.println("\u001B[31mUnknown user role.\u001B[0m");
                break;
        }
    } else {
        System.out.println("\u001B[31m \nLogin failed. Invalid user ID or password.\u001B[0m");
    }

    scanner.close();
}

public void changePassword() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter your user ID: ");
    String userId = scanner.nextLine();
    System.out.print("Enter your current password: ");
    String currentPassword = scanner.nextLine();

    // Authenticate the user first
    User authenticatedUser = userController.authenticateUser(userId, currentPassword);

    if (authenticatedUser != null) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();

        // Call a method in the UserController to handle the password change
        boolean success = userController.changePassword(authenticatedUser, newPassword);

        if (success) {
            System.out.println("\u001B[32m \nPassword change successful.\u001B[0m");
        } else {
            System.out.println("\u001B[31m \nPassword change failed.\u001B[0m");
        }
    } else {
        System.out.println("\u001B[31m \nAuthentication failed. Invalid user ID or password.\u001B[0m");
    }

    scanner.close();
}


    private void displayStaffFeatures() {
    Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Edit Camps");
        System.out.println("2. View All Camps");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                editCamp();
                break;
            case 2:
                viewAllCamps();
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }

        scanner.close();
    }

    private void editCamp() {
        // Add logic to join a camp
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[32mEdit camp successfully.\u001B[0m");
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Go back to the previous menu");
        System.out.println("2. Exit the application");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayStudentFeatures();  // Assuming this is the previous menu
                break;
            case 2:
                System.out.println("\nExiting. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        
        scanner.close();

    }

    private void viewAllCamps() {
        // Add logic to view camps
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[32mDisplaying all camps...\u001B[0m");
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Go back to the previous menu");
        System.out.println("2. Exit the application");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayStudentFeatures();  // Assuming this is the previous menu
                break;
            case 2:
                System.out.println("\nExiting. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        
        scanner.close();

    }
    private void displayStudentFeatures() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Join Camps");
        System.out.println("2. View Camps");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                joinCamp();
                break;
            case 2:
                viewCamps();
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }

        scanner.close();
    }

    private void joinCamp() {
        // Add logic to join a camp
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[32mJoin camp successfully.\u001B[0m");
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Go back to the previous menu");
        System.out.println("2. Exit the application");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayStudentFeatures();  // Assuming this is the previous menu
                break;
            case 2:
                System.out.println("\nExiting. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        
        scanner.close();

    }

    private void viewCamps() {
        // Add logic to view camps
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[32mDisplaying camps...\u001B[0m");
        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Go back to the previous menu");
        System.out.println("2. Exit the application");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayStudentFeatures();  // Assuming this is the previous menu
                break;
            case 2:
                System.out.println("\nExiting. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        
        scanner.close();
    }

}
