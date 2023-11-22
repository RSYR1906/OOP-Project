package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.StaffController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;

import java.util.List;
import java.util.Scanner;

public class LoginUI implements UI{

    private final UserController userController;
    private StaffRepository staffRepository;
    private StudentRepository studentRepository;
    private CampRepository campRepository;
    private CampController campController;
    private StaffController staffController;


    public LoginUI(UserController userController, StaffRepository staffRepository, StudentRepository studentRepository, CampRepository campRepository, CampController campController, StaffController staffController) {
        this.userController = userController;
        this.staffRepository = staffRepository;
        this.studentRepository = studentRepository;
        this.campRepository = campRepository;
        this.campController = campController;
        this.staffController = staffController;

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
        System.out.println("2. Change Password");
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
            System.out.println("\u001B[33m\nLogin successful. Welcome " + authenticatedUser.getName().toUpperCase() + "!\u001B[0m");
            System.out.println("\u001B[34m\nUserID:\u001B[0m " + authenticatedUser.getID());
            System.out.println("\u001B[34mFaculty:\u001B[0m " + authenticatedUser.getFaculty());
            String userRole = userController.getUserRole(authenticatedUser);
        System.out.println("\u001B[34mUser Role:\u001B[0m " + userRole);

        switch (userRole) {
            case "Staff Member":
            StaffUI staffUI = new StaffUI(userController, campController, staffController, campRepository);
            staffUI.displayStaffFeatures();                
                break;
            case "Student":
            StudentUI studentUI = new StudentUI(userController, null, null);
            studentUI.displayStudentFeatures();                
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

        String userRole = userController.getUserRole(authenticatedUser);
        boolean success;

        // Call a method in the UserController to handle the password change
    switch(userRole){
        case "Staff Member":
            // Assign the result to the success variable
            success = staffRepository.updateStaffPassword(authenticatedUser.getID(), newPassword);
            if (success) {
                System.out.println("\u001B[32m \nPassword change successful.\u001B[0m");
            } else {
                System.out.println("\u001B[31m \nPassword change failed.\u001B[0m");
            }
            break;
        case "Student":
            // Assign the result to the success variable
            success = studentRepository.updateStudentPassword(authenticatedUser.getID(), newPassword);
            if (success) {
                System.out.println("\u001B[32m \nPassword change successful.\u001B[0m");
            } else {
                System.out.println("\u001B[31m \nPassword change failed.\u001B[0m");
            }
            break;
        default:
            System.out.println("\u001B[31m \nAuthentication failed. Invalid user ID or password.\u001B[0m");
            break;
}


    scanner.close(); 
}
}
}
