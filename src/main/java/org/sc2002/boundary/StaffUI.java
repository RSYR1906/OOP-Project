// StaffUI.java
package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.StaffController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;

import java.util.List;
import java.util.Scanner;

public class StaffUI {

    private final UserController userController;
    private final CampController campController;  // You need a reference to CampController
    private final StaffController staffController;
    private final CampRepository campRepository;



    public StaffUI(UserController userController, CampController campController, StaffController staffController, CampRepository campRepository) {
        this.userController = userController;
        this.campController = campController;  // Initialize CampController
        this.staffController = staffController;
        this.campRepository = campRepository;

    }

    public void displayStaffFeatures() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nChoose an option:");
        System.out.println("1. Toggle camp visibility");
        System.out.println("2. View All Camps");
        System.out.println("3. View list of my created camps");
        System.out.println("4. View enquiries");
        System.out.println("5. View Suggestions");
        System.out.println("6. Generate report of students attending my camp");
        System.out.println("7. Exit");



        System.out.print("\nYour choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                //toggleVisibilityOfCamp();
                break;
            case 2:
                viewAllCamps();
                break;
            case 3:
               // viewMyCreatedCamps();
                break;
            case 7:
                System.out.println("\nExiting. Goodbye!");
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
        List<Camp> allCamps = campRepository.getAllCamps();
        System.out.println("\u001B[32mDisplaying all camps...\u001B[0m\n");

        for (Camp camp : allCamps) {
        System.out.println("=========================");
        System.out.println(camp.toStringWithSeparator("\n"));}

        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Edit camp");
        System.out.println("2. Go back to the previous menu");
        System.out.println("3. Exit the application");

        System.out.print("\nYour choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                //editCamp();  // Assuming this is the previous menu
               // break;
            case 2:
                displayStaffFeatures();  // Assuming this is the previous menu
                break;
            case 3:
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
    


