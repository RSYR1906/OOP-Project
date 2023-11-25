package org.sc2002.boundary;

import org.sc2002.controller.UserController;
import org.sc2002.entity.User;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.Scanner;

/**
 * The type Login ui.
 */
public class LoginUI implements UI<User> {

    private UserController userController;

    /**
     * Instantiates a new Login ui.
     *
     * @param userController the user controller
     */
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
        System.out.println("\u001B[36m  | |   | |  |  |___   |  |___   |  |___   | |__| |   __    \u001B[0m");
        System.out.println("\u001B[36m  |_|   |_|  |______|  |______|  |______|  |______|  |__|   \u001B[0m");

        System.out.println("\u001B[36m\n =================================================================\u001B[0m");
        System.out.println("\u001B[36m     Welcome to Camp Application and Management System (CAMs)    \u001B[0m");
        System.out.println("\u001B[36m =================================================================\u001B[0m");

        System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Change Password");
            System.out.println("3. Exit");

            System.out.print("\nYour choice (1-3): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    User user = login();
                    if (user != null) return user;
                    break;

                case 2:
                    User userChange = changePassword();
                    if (userChange != null) return userChange;
                    break;
                case 3:
                    System.out.println("\nExiting. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }


    }

    /**
     * Login user.
     *
     * @return the user
     */
    public User login() {
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
            return authenticatedUser;
        } else {
            System.out.println("\u001B[31m \nLogin failed. Invalid user ID or password.\u001B[0m");
            return null;
        }


    }

    /**
     * Change password user.
     *
     * @return the user
     */
    public User changePassword() {
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
            authenticatedUser.setPassword(newPassword);
            // Call a method in the UserController to handle the password change
            boolean success = userController.changePassword(authenticatedUser, newPassword);
//            boolean success = true;
            if (success) {
                System.out.println("\u001B[32m \nPassword change successful.\u001B[0m");
                return authenticatedUser;
            } else {
                System.out.println("\u001B[31m \nPassword change failed.\u001B[0m");

            }
        } else {
            System.out.println("\u001B[31m \nAuthentication failed. Invalid user ID or password.\u001B[0m");

        }

        return null;
    }
}
