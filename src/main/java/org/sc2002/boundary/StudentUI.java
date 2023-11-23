package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.EnquiryController;
import org.sc2002.controller.StudentController;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.*;

import java.util.Scanner;

public class StudentUI implements UI{

    private StudentController studentController;
    private CampController campController;
    private EnquiryController enquiryController;

    private Student student;

    public StudentUI(StudentController studentController, Student student, EnquiryController enquiryController, CampController campController) {
        this.studentController = studentController;
        this.student = student;
        this.enquiryController = enquiryController;
        this.campController = campController;
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
            System.out.println("1. View the list of camps along with the remaining available slots.");
            System.out.println("2. Choose a camp and register for either as a camp attendee or camp committee");
            System.out.println("3. Submit enquiries regarding a camp");
            System.out.println("4. view your enquiries");
            System.out.println("5. Edit your enquiries");
            System.out.println("6. Delete your enquiries");
            System.out.println("7. View the camps that you have already registered for");
            System.out.println("8. withdraw from camps that you have already registered for.");
            System.out.println("9. Exit");

            System.out.print("\nYour choice (1-7): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    //viewCampsRemain();
                    break;
                case 2:
                    //registerCamps();
                    break;
                case 3:
                    //submitEnquiries();
                    break;
                case 4:
                    //viewEnquiries();
                    break;
                case 5:
                    //editEnquiries();
                    break;
                case 6:
                    //deleteEnquery();
                    break;
                case 7:
                    System.out.println("\nExiting. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }


}
