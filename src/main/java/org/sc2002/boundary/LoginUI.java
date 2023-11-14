package org.sc2002.boundary;

import org.sc2002.controller.UserController;
import org.sc2002.entity.User;

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
                // Implement Forget UserID logic
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
            System.out.println("\u001B[34mUser Role:\u001B[0m " + userController.getUserRole(authenticatedUser));
        } else {
            System.out.println("\u001B[31m \nLogin failed. Invalid user ID or password.\u001B[0m");
        }

        scanner.close();
    }
}
