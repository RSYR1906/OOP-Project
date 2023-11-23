package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.StaffController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;

import java.util.Scanner;

public class StaffUI implements UI{

    private StaffController staffController;
    private CampController campController;
    private UserController userController;
    //private CommitteeController committeeController;
    private CampRepository campRepository;
    private User user;

    public StaffUI(StaffController staffController, CampController campController, UserController userController, CampRepository campRepository, User user) {
        this.staffController = staffController;
        this.userController = userController;
        this.campController = campController;
        this.campRepository = campRepository;
        this.user = user;
    }

    @Override
    public void body() {
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
            System.out.println("1. Create, edit and delete camps.");
            System.out.println("2. Toggle the visibility of the camp");
            System.out.println("3. View all camps");
            System.out.println("4. View all camps you have created");
            System.out.println("5. View and reply to enquiries from students to the camp(s) you have created");
            System.out.println("6. View and approve suggestions to changes to camp details from camp committee.");
            System.out.println("7. Generate a report of the list of students attending each camp you has created.");
            System.out.println("8. Generate a performance report of the camp committee members.");
            System.out.println("9. 直接退出");

            System.out.print("\nYour choice (1-9): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    //changeCamp(user);
                    break;
                case 2:
                    //changeVisibility();
                    break;
                case 3:
                    //viewAllCamp();
                    break;
                case 4:
                    //viewAllMyCreateCamp(user);
                    break;
                case 5:
                    //viewAndReply(user);
                    break;
                case 6:
                    //viewAndApproveSuggestion(user);
                    break;
                case 7:
                    //GenerateStudentsReport(user);
                    break;
                case 8:
                    //GeneratePerformanceReport(user);
                    break;

                case 9:
                    System.out.println("\nExiting. Goodbye!");
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }
}
