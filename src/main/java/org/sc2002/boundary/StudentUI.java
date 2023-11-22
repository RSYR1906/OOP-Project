// StudentUI.java
package org.sc2002.boundary;

import org.sc2002.entity.User;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.controller.StudentController;
import org.sc2002.controller.UserController;
import org.sc2002.repository.CampRepository;

import java.util.List;
import java.util.Scanner;

public class StudentUI {

    private final UserController userController;
    private final StudentController studentController;
    private final CampRepository campRepository;

    public StudentUI(UserController userController, StudentController studentController, CampRepository campRepository) {
        this.userController = userController;
        this.studentController = studentController;
        this.campRepository = campRepository;
    }

    public void displayStudentFeatures() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nChoose an option:");
        System.out.println("1. Join Camps");
        System.out.println("2. View Camps");
        System.out.println("3. Withdraw from camp");
        System.out.println("4. Write enquiries");
        System.out.println("5. Exit");



        System.out.print("\nYour choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                joinCamp();
                break;
            case 2:
                viewCamps();
                break;
            case 3:
                withdrawFromCamp();
                break;
            case 5:
                System.out.println("\nExiting. Goodbye!");
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }

        scanner.close();
    }

    // Other methods like joinCamp() and viewCamps() go here
     private void joinCamp() {
        // Add logic to join a camp
        Scanner scanner = new Scanner(System.in);
        List<Camp> camps = campRepository.getAllCamps();
    // Check if there are any camps available
        if (camps.isEmpty()) {
            System.out.println("\u001B[31mNo camps available to join.\u001B[0m");
        }

        // Display camps with an index
        System.out.println("Available Camps:");
        for (int i = 0; i < camps.size(); i++) {
            Camp camp = camps.get(i);
            System.out.println((i + 1) + ". " + camp.getCampName() + " - " + camp.getDescription());
        }

        // Prompt the user to choose a camp
        System.out.print("\nEnter the number of the camp you want to join: ");
        int campIndex = scanner.nextInt();

        // Validate the selected index
        if (campIndex < 1 || campIndex > camps.size()) {
            System.out.println("\u001B[31mInvalid selection. Please try again.\u001B[0m");
        }

        // Get the selected camp
        Camp selectedCamp = camps.get(campIndex - 1);
        Student student = (Student) userController.getCurrentUser();

        try {
            studentController.registerStudentToCamp(student, selectedCamp);
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
 }        } catch (Exception e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
                
                scanner.close();

    }

   private void viewCamps() {
    Scanner scanner = new Scanner(System.in);

    // Retrieve the list of camps within this method
    List<Camp> camps = campRepository.getAllCamps();

    // Display the header
    System.out.println("\u001B[34m--- Available Camps ---\u001B[0m");
    System.out.printf("%-5s %-20s %-15s %s\n", "No.", "Camp Name", "Dates", "Description");

    // Display each camp with an index
    for (int i = 0; i < camps.size(); i++) {
        Camp camp = camps.get(i);
        String campInfo = String.format("%-5d %-20s %-15s %s",
                (i + 1),
                camp.getCampName(), // Assuming Camp has a method getCampName()
                camp.getCampStartDate() + " to " + camp.getCampEndDate(), // Assuming Camp has methods getCampStartDate() and getCampEndDate()
                camp.getDescription()); // Assuming Camp has a method getDescription()
        System.out.println(campInfo);
    }

    // Rest of the method for user interaction
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
    private void withdrawFromCamp() {

}
}
