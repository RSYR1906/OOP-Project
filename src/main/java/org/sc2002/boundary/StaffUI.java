package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.StaffController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.entity.User;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StaffUI implements UI{

    private StaffController staffController;
    private CampController campController;
    private UserController userController;
    //private CommitteeController committeeController;
    private CampRepository campRepository;
    private Staff staff;

    public StaffUI(StaffController staffController, CampController campController, UserController userController, CampRepository campRepository, Staff staff) {
        this.staffController = staffController;
        this.userController = userController;
        this.campController = campController;
        this.campRepository = campRepository;
        this.staff = staff;
    }

    @Override
    public Object body() {
        Scanner scanner = new Scanner(System.in);

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
            System.out.println("9. Exit");

            System.out.print("\nYour choice (1-9): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    changeCamp();
                    break;
                case 2:
                    //changeVisibility();
                    break;
                case 3:
                    viewAllCamps();
                    break;
                case 4:
                    viewCampsICreated();
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }



    public List<Camp> viewAllCamps() {
        System.out.println("Viewing All camps");
        List<Camp> camps = staffController.viewAllCamps();
        printCamps(camps);
        return camps;
    }

    public List<Camp> viewCampsICreated() {
        System.out.println("Viewing camps I created");
        List<Camp> camps = staffController.viewAllCamps();
        List<Camp> campsICreated = camps.stream().filter(camp -> camp.getStaffInChargeID().equals(staff.getID())).collect(Collectors.toList());
        printCamps(campsICreated);
        return campsICreated;
    }



    public void printCamps(List<Camp> camps){
        int index = 0;
        for (Camp camp : camps) {
            String campId = camp.getID();
            String campName = camp.getCampName();
            String description = camp.getDescription();
            LocalDate campStartDate = camp.getCampStartDate();
            LocalDate campEndDate = camp.getCampEndDate();
            LocalDate campRegistrationEndDate = camp.getCampRegistrationEndDate();
            Faculty userGroupOpenTo = camp.getUserGroupOpenTo();
            String location = camp.getLocation();
            String staffInChargeID = camp.getStaffInChargeID();
            int totalSlots = camp.getTotalSlots();
            int campCommitteeSlots = camp.getCampCommitteeSlots();
            boolean visibilityToStudent = camp.getVisibilityToStudent();
            System.out.println("\u001B[34mCamp Number:\u001B[0m " + index++);
            System.out.println("\u001B[34mCamp Name:\u001B[0m " + campName);
            System.out.println("\u001B[34mCamp Start Date:\u001B[0m " + campStartDate);
            System.out.println("\u001B[34mRegistration closing date:\u001B[0m " + campEndDate);
            System.out.println("\u001B[34mCamp Registration End Date:\u001B[0m " + campRegistrationEndDate);
            System.out.println("\u001B[34mUser group:\u001B[0m " + userGroupOpenTo);
            System.out.println("\u001B[34mLocation:\u001B[0m " + location);
            System.out.println("\u001B[34mTotal Slots:\u001B[0m " + totalSlots);
            System.out.println("\u001B[34mCamp Committee Slots:\u001B[0m " + campCommitteeSlots);
            System.out.println("\u001B[34mDescription:\u001B[0m " + description);
            System.out.println("\u001B[34mStaff in charge:\u001B[0m " + staffInChargeID);
            System.out.print("\u001B[34mCamp is \u001B[0m ");
            System.out.println(visibilityToStudent ? "visible" : "invisible");
            System.out.println("--------------next one---------------");
        }
    }

    public void changeCamp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nYour choice (1-3): ");
        System.out.println("\n1. create\t2.edit\t3.delete");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                //createCamp(staff);
                break;
            case 2:
                editCamp();
                break;
            case 3:
                deleteCamp();
                break;
            default:
                System.out.println();
                break;
        }
    }

    public void editCamp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want to edit camp you should enter the following details!");
        System.out.println("Please enter the following details:");

        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();
        String campId=null;
        LocalDate campStartDate = null;
        LocalDate campEndDate = null;
        LocalDate campRegistrationEndDate = null;

        try{
            Camp camp = campRepository.getCampByID(campName);
            if(!camp.getStaffInChargeID().equals(staff.getID())){
                throw new WrongStaffException();
            }
            campId= camp.getID();
            campStartDate = camp.getCampStartDate();
            campEndDate = camp.getCampEndDate();
            campRegistrationEndDate = camp.getCampRegistrationEndDate();
        } catch (EntityNotFoundException | WrongStaffException e) {
            System.out.println("Failed to edit camp: " + e.getMessage());
            return;
        }


        Faculty userGroupOpenTo;
        while (true) {
            System.out.print("User group (own school or whole NTU): Please enter 0： own school;     1: whole NTU");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                userGroupOpenTo = staff.getFaculty();
                break;
            } else if (userInput.equals("1")) {
                userGroupOpenTo = Faculty.ALL;
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format 0/1.");
            }
        }


        System.out.print("Location: ");
        String location = scanner.nextLine();

        int committeeSlots = -1;
        while (committeeSlots < 0 || committeeSlots > 10) {
            System.out.print("Camp Committee Slots (max 10): ");
            committeeSlots = scanner.nextInt();
        }

        int totalSlots = 0;
        while (totalSlots <= committeeSlots) {
            System.out.print("Total Slots (must be greater than the number of committee): ");
            totalSlots = scanner.nextInt();
        }

        scanner.nextLine(); // consume the newline character

        System.out.print("Description: ");
        String description = scanner.nextLine();

        // Assume the staff in charge is tied to the staff who created it
        System.out.println("Automatically tied to the staff who created it");
        String staffInCharge = staff.getID();

        boolean visibilityToStudent = true;
        while (true) {
            System.out.print("Is the current campsite visible to students;  0： invisible;     1: visible");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                visibilityToStudent = false;
                break;
            } else if (userInput.equals("1")) {
                visibilityToStudent = true;
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format 0/1.");
            }
        }


        Camp editedCamp = new Camp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, committeeSlots, staffInCharge, visibilityToStudent);
        try {
            campController.editCamp(editedCamp);
            System.out.println("Successfully edited camp: " + editedCamp.getCampName());
        }catch (Exception e){
            System.out.println("Failed to edit camp: " + e.getMessage());
        }
    }

    public void deleteCamp() {
        List<Camp> camps = viewCampsICreated();
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want delete camp, you should enter the following details!");
        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();

        try{
            Camp campToDelete = campRepository.getCampByID(campName);
            if(!campToDelete.getStaffInChargeID().equals(staff.getID())){
                throw new WrongStaffException();
            }
            campController.deleteCamp(campToDelete.getID());
            System.out.println("Successfully deleted camp: " + campToDelete.getCampName());
        } catch (EntityNotFoundException | WrongStaffException e) {
            System.out.println("Failed to delete camp: " + e.getMessage());
        }
    }


}
