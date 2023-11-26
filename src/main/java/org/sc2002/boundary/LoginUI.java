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
    public User body() {
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
        System.out.println("2. Exit");

        System.out.print("\nYour choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                User user = login();
                if (user != null) return user;
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
        return null;
    }

    public User login() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        // Get user input for user ID and password
        System.out.print("\nEnter your user ID (or type 'exit' to go back): ");
        String userId = scanner.nextLine();

        // Allow user to exit the loop
        if ("exit".equalsIgnoreCase(userId)) {
        System.out.print("\u001B[35m\nExiting. Goodbye! \u001B[0m");
        System.exit(0);
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User authenticatedUser = userController.authenticateUser(userId, password);

        if (authenticatedUser != null) {
            System.out.println("\u001B[33m \nLogin successful. Welcome " + authenticatedUser.getName().toUpperCase() + "!\u001B[0m");
            System.out.println("\u001B[34mFaculty:\u001B[0m " + authenticatedUser.getFaculty());
            String userRole = userController.getUserRole(authenticatedUser);
            System.out.println("\u001B[34mUser Role:\u001B[0m " + userRole);

            return authenticatedUser;
        } else {
            System.out.println("\u001B[31m \nLogin failed. Invalid user ID or password.\u001B[0m");
        }
    }
}
}
