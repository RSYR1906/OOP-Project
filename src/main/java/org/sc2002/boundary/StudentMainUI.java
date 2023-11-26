package org.sc2002.boundary;

import org.sc2002.controller.*;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class StudentMainUI implements UI{

    private Student student;
    private StudentController studentController;
    private UserController userController;
    private CampController campController;
    private EnquiryController enquiryController;

    private StudentEnquiryUI studentEnquiryUI;

    private CommitteeUI committeeUI;



    public StudentMainUI(Student student, StudentController studentController, UserController userController, CampController campController, EnquiryController enquiryController, SuggestionController suggestionController) {
        this.student = student;
        this.studentController = studentController;
        this.enquiryController = enquiryController;
        this.campController = campController;
        this.studentEnquiryUI = new StudentEnquiryUI(student, studentController, campController, enquiryController);
        this.committeeUI = new CommitteeUI(student, studentController, campController, enquiryController, suggestionController);
    }

    @Override
    public Object body() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\u001B[36m\nSTUDENT MAIN MENU:\u001B[0m");
            System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
            System.out.println("1. View the list of camps along with the remaining available slots.");
            System.out.println("2. Choose a camp and register for either as a camp attendee or camp committee");
            System.out.println("3. View the camps that you have already registered for");
            System.out.println("4. Withdraw from camps that you have already registered for.");
            System.out.println("5. Manage your student enquiries");
            System.out.println("6. Manage your committee activities");
            System.out.println("7. Change your password");
            System.out.println("8. ");
            System.out.println("9. Exit");

            System.out.print("\nYour choice (1-9): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewCampsRemain();
                    break;
                case 2:
                    registerCamps();
                    break;
                case 3:
                    printRegisteredCamps();
                    break;
                case 4:
                    withdrawFromCamp();
                    break;
                case 5:
                    studentEnquiryUI.body();
                    break;
                case 6:
                    committeeUI.body();
                    break;
                case 7:
                    changePassword();
                    break;
                case 8:
                    break;
                case 9:
                    System.out.println("\nExiting. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }

    Map<String, Integer> viewCampsRemain() {
        Map<String, Integer> campSlotsMap = studentController.getCampRemainSlots(student);

        // Use StringBuilder for efficient string concatenation
        StringBuilder sb = new StringBuilder();

        // Add header to the output
        sb.append("\u001B[34mcamps, RemainSlots\u001B[0m\n");

        // Iterate over the map and add each entry to the output
        campSlotsMap.forEach((key, value) -> sb.append("\u001B[0m").append(key).append(", ").append(value).append("\n"));

        // Print the result
        System.out.println(sb.toString());

        return campSlotsMap;

    }

    void registerCamps() {
        Map<String, Integer> campSlotsMap = viewCampsRemain();
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while (!campSlotsMap.containsKey(userInput)) {
            System.out.println("Please enter the name of the camp you want to join");
            userInput = scanner.nextLine();
            if (!campSlotsMap.containsKey(userInput)) System.out.println("Please enter the correct camp name");
        }


        System.out.println("Please enter whether to register as an attendee or a committee member: 0 member; 1 committee");
        int input = scanner.nextInt();
        try{
            Camp camp = campController.getCamp(userInput);
            if (input == 0) {
                studentController.registerCampAsStudent(student, camp);
            } else if (input == 1) {
                studentController.registerCampAsCampCommitteeMember(student, camp);
            }

            System.out.println("Successfully registered for: " + camp.getCampName());



        } catch (Exception e){
            System.out.println("Failed to register: " + e.getMessage());
        }
    }

    void withdrawFromCamp(){
        ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (true) {
            System.out.println("Please enter the name of the camp you want to withdraw from");
            userInput = scanner.nextLine();
            String finaluserInput = userInput;
            if (registeredCamps.stream().anyMatch(camp -> camp.getCampName().equals(finaluserInput))) {
                break;
            } else {
                System.out.println("Please enter the correct camp name");
            }
        }

        try{
            Camp camp = campController.getCamp(userInput);
            studentController.withdrawFromCamp(student, camp);
            System.out.println("Successfully withdrawn from: " + camp.getCampName());

        } catch (Exception e){
            System.out.println("Failed to Withdraw: " + e.getMessage());
        }
    }

    void printRegisteredCamps(){
        studentController.printRegisteredCamps(student);
    }

    public void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        studentController.changePassword(student, newPassword);
    }


}
