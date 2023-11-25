package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.EnquiryController;
import org.sc2002.controller.StudentController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.*;

import java.util.ArrayList;
import java.util.Map;
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

    public StudentUI(StudentController studentController2, CampController campController2,
            UserController userController, CampRepository campRepository, Student user) {
    }

    @Override
    public Object body() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
        while (true) {
            System.out.println("1. View the list of camps along with the remaining available slots.");
            System.out.println("2. Choose a camp and register for either as a camp attendee or camp committee");
            System.out.println("3. Submit enquiries regarding a camp");
            System.out.println("4. View your enquiries");
            System.out.println("5. Edit your enquiries");
            System.out.println("6. Delete your enquiries");
            System.out.println("7. View the camps that you have already registered for");
            System.out.println("8. Withdraw from camps that you have already registered for.");
            System.out.println("9. Logout");

            System.out.print("\nYour choice (1-7): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewCampsRemain();
                    break;
                case 2:
                    registerCamps();
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
                    printRegisteredCamps();
                    break;
                case 8:
                    withdrawFromCamp();
                    break;
                case 9:
                    return logout(student);
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }
    }
        
    public boolean logout(User user) {
        System.out.println("\u001B[33mYou have successfully logged out.\u001B[0m\n");
        return true; // Indicate the user has logged out
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
            Camp camp = studentController.getCamp(userInput);
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
            Camp camp = studentController.getCamp(userInput);
            studentController.withdrawFromCamp(student, camp);
            System.out.println("Successfully withdrawn from: " + camp.getCampName());

        } catch (Exception e){
            System.out.println("Failed to Withdraw: " + e.getMessage());
        }
    }

    void printRegisteredCamps(){
        studentController.printRegisteredCamps(student);
    }
}
