package org.sc2002.boundary;

import org.sc2002.controller.UserController;
import org.sc2002.entity.User;

import java.util.Scanner;

public class LoginUI {

    private final UserController userController;

    public LoginUI(UserController userController) {
        this.userController = userController;
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("   _     _    ______    __        __        ______    __  ");
        System.out.println("  | |   | |  |  ____|  |  |      |  |      |  __  |  |  |   ");
        System.out.println("  | |___| |  |  |___   |  |      |  |      | |  | |  |  |   ");
        System.out.println("  |  ___  |  |  ____|  |  |      |  |      | |  | |  |__|   ");
        System.out.println("  | |   | |  |  |___   |  |___   |  |___   | |__| |   __    " );
        System.out.println("  |_|   |_|  |______|  |______|  |______|  |______|  |__| ");

        System.out.println("\u001B[36m\n =================================================================\u001B[0m");
        System.out.println("\u001B[36m     Welcome to Camp Application and Management System (CAMs)    \u001B[0m");
        System.out.println("\u001B[36m =================================================================\u001B[0m");

        // Get user input for user ID and password
        System.out.print("\u001B[33m\nEnter your user ID:\u001B[0m ");
        String userId = scanner.nextLine();
        System.out.print("\u001B[33mEnter your password:\u001B[0m ");
        String password = scanner.nextLine();

        User authenticatedUser = userController.authenticateUser(userId, password);

        if (authenticatedUser != null) {
            System.out.println("\u001B[32m \nLogin successful. Welcome, " + authenticatedUser.getID() + "!\u001B[0m");
            System.out.println("\u001B[34mFaculty:\u001B[0m " + authenticatedUser.getFaculty());
            System.out.println("\u001B[34mUser Role:\u001B[0m " + userController.getUserRole(authenticatedUser));
        } else {
            System.out.println("\u001B[31m \nLogin failed. Invalid user ID or password.\u001B[0m");
        }

        //System.out.println("\u001B[36m============================\u001B[0m");

        scanner.close();
    }
}

