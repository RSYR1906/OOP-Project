package org.sc2002.boundary;

import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import static org.sc2002.utils.CAMSDateFormat.formatStringToDate;

public class StaffUI implements UI{

    private Staff staff;
    private StaffController staffController;
    private CampController campController;

    private StudentController studentController;
    private EnquiryController enquiryController;
    private SuggestionController suggestionController;

    private StaffCampReportGeneration staffCampReportGeneration;

    private CommitteePerformanceReportGeneration committeePerformanceReportGeneration;


    public StaffUI(Staff staff, StaffController staffController, StudentController studentController, UserController userController, CampController campController, EnquiryController enquiryController, SuggestionController suggestionController) {
        this.staff = staff;
        this.staffController = staffController;
        this.studentController = studentController;
        this.campController = campController;
        this.enquiryController = enquiryController;
        this.suggestionController = suggestionController;
        this.staffCampReportGeneration = new StaffCampReportGeneration(campController);
        this.committeePerformanceReportGeneration = new CommitteePerformanceReportGeneration(studentController);
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
            System.out.println("9. Change password");
            System.out.println("10. Exit");

            System.out.print("\nYour choice (1-9): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    changeCamp();
                    break;
                case 2:
                    toggleVisibility();
                    break;
                case 3:
                    viewAllCamps();
                    break;
                case 4:
                    viewCampsICreated();
                    break;
                case 5:
                    replyEnquiryAsStaff();
                    break;
                case 6:
                    approveSuggestion();
                    break;
                case 7:
                    generateStaffCampReport();
                    break;
                case 8:
                    generatePerformanceReport();
                    break;
                case 9:
                    changePassword();
                    break;
                case 10:
                    System.out.println("\nExiting. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }



    public void viewAllCamps() {
        System.out.println("Viewing All camps");
        List<Camp> camps = campController.getAllCamps();
        int index = 0;
        for(Camp camp : camps){
            System.out.println("\u001B[34mCamp Number:\u001B[0m " + index++);
            printCamp(camp);
        }
    }

    public void viewCampsICreated() {
        System.out.println("Viewing camps I created");
        List<Camp> campsICreated = staffController.getAllCampsICreated(staff.getID());
        int index = 0;
        for(Camp camp : campsICreated){
            System.out.println("\u001B[34mCamp Number:\u001B[0m " + index++);
            printCamp(camp);
        }
    }

    public void printCamp(Camp camp){
        System.out.println("\u001B[34mCamp Name:\u001B[0m " + camp.getCampName());
        System.out.println("\u001B[34mCamp Start Date:\u001B[0m " + camp.getCampStartDate());
        System.out.println("\u001B[34mRegistration closing date:\u001B[0m " + camp.getCampEndDate());
        System.out.println("\u001B[34mCamp Registration End Date:\u001B[0m " + camp.getCampRegistrationEndDate());
        System.out.println("\u001B[34mUser group:\u001B[0m " + camp.getUserGroupOpenTo());
        System.out.println("\u001B[34mLocation:\u001B[0m " + camp.getLocation());
        System.out.println("\u001B[34mTotal Slots:\u001B[0m " + camp.getTotalSlots());
        System.out.println("\u001B[34mCamp Committee Slots:\u001B[0m " + camp.getCampCommitteeSlots());
        System.out.println("\u001B[34mDescription:\u001B[0m " + camp.getDescription());
        System.out.println("\u001B[34mStaff in charge:\u001B[0m " + camp.getStaffInChargeID());
        System.out.print("\u001B[34mCamp is \u001B[0m ");
        System.out.println(camp.getVisibilityToStudent() ? "visible" : "invisible");
        System.out.println("------------------------------------");
    }

    public void toggleVisibility() {
        List<Camp> camps = staffController.getAllCampsICreated(staff.getID());
        viewCampsICreated();
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want change visibility of the camp, you should enter the following details!");
        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();

        try{
            Camp campToChangeVisibility = campController.getCamp(campName);
            if(!campToChangeVisibility.getStaffInChargeID().equals(staff.getID())){
                throw new WrongStaffException();
            }
            System.out.println("Current visibility of " + campToChangeVisibility.getCampName() + " : " + campToChangeVisibility.getVisibilityToStudent());
            staffController.toggleVisibilityOfCamp(campToChangeVisibility, staff);
            System.out.println("Updated visibility of " + campToChangeVisibility.getCampName() + " : " + campToChangeVisibility.getVisibilityToStudent());
        } catch (EntityNotFoundException | WrongStaffException e) {
            System.out.println("Failed to change visibility of the camp: " + e.getMessage());
        }

    }

    public void changeCamp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nYour choice (1-3): ");
        System.out.println("\n1. create\t2.edit\t3.delete");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                createCamp();
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
            Camp camp = campController.getCamp(campName);
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
            staffController.editCamp(editedCamp);
            System.out.println("Successfully edited camp: " + editedCamp.getCampName());
        }catch (Exception e){
            System.out.println("Failed to edit camp: " + e.getMessage());
        }
    }

    public void deleteCamp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want delete camp, you should enter the following details!");
        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();

        try{
            Camp campToDelete = campController.getCamp(campName);
            if(!campToDelete.getStaffInChargeID().equals(staff.getID())){
                throw new WrongStaffException();
            }
            staffController.deleteCamp(campToDelete, staff);
            System.out.println("Successfully deleted camp: " + campToDelete.getCampName());
        } catch (EntityNotFoundException | WrongStaffException e) {
            System.out.println("Failed to delete camp: " + e.getMessage());
        }
    }

      /**
     * create camp
     */
    public void createCamp() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[36mWELCOME TO CAMP CREATOR!\n\u001B[0m");
        System.out.println("\u001B[36mPlease enter the following details:\u001B[0m");

        System.out.print("\u001B[34mCamp Name: \u001B[0m");
        String campName = scanner.nextLine();

        LocalDate campStartDate = null;
        while (true) {
            System.out.print("\u001B[34mCamp Start Date (format: YYYY-MM-DD): \u001B[0m");
            String dateString = scanner.nextLine();
            try{
                campStartDate = formatStringToDate(dateString);
                break;
            } catch (DateTimeException e){
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }

        LocalDate campEndDate = null;
        while (true) {
            System.out.print("\u001B[34mCamp End Date (format: YYYY-MM-DD): \u001B[0m"); // Need to add check that start date is before end date
            String closingDateString = scanner.nextLine();
            try{
                campEndDate = formatStringToDate(closingDateString);
                break;
            } catch (DateTimeException e){
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }

        LocalDate campRegistrationEndDate = null;
        while (true) {
            System.out.print("\u001B[34mCamp Registration End Date (format: YYYY-MM-DD): \u001B[0m");
            String closingDateString = scanner.nextLine();
            try{
                campRegistrationEndDate = formatStringToDate(closingDateString);
                break;
            } catch (DateTimeException e){
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }
        Faculty userGroupOpenTo;
        while (true) {
            System.out.print("\n\u001B[34mUser group (own school or whole NTU): Please enter \u001B[0m" + // Blue color for the question
                            "\u001B[32m0: own school \u001B[0m" + // Green color for option 0
                            "\u001B[31m1: whole NTU \u001B[0m");  // Red color for option 1
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


        System.out.print("\u001B[34mLocation: \u001B[0m");
        String location = scanner.nextLine();

        int committeeSlots = -1;
        while (committeeSlots < 0 || committeeSlots > 10) {
            System.out.print("\n\u001B[34mCamp Committee Slots (max 10): \u001B[0m");
            committeeSlots = scanner.nextInt();
        }

        int totalSlots = 0;
        while (totalSlots <= committeeSlots) {
            System.out.print("\u001B[34mTotal Slots (must be greater than the number of committee): \u001B[0m");
            totalSlots = scanner.nextInt();
        }

        scanner.nextLine(); // consume the newline character

        System.out.print("\u001B[34mDescription: \u001B[0m");
        String description = scanner.nextLine();

        // Assume the staff in charge is tied to the staff who created it
        System.out.println("Automatically tied to the staff who created it");

        boolean visibilityToStudent = true;
        while (true) {
            System.out.print("\u001B[34mIs the current campsite visible to students?  0：invisible  1:visible\u001B[0m ");
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
        try {
            Camp createdCamp = campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, committeeSlots, staff.getID(), visibilityToStudent);
            System.out.println("\n\u001B[33m Successfully created a camp\u001B[0m ");
            printCamp(createdCamp);
        } catch (Exception e) {
            System.out.println("Failed to create the camp : " + e.getMessage());
        }
    }


    void printEnquiries(){
        List<Enquiry> enquiries = enquiryController.getEnquiryByStaff(staff);
        int index = 0;
        for (Enquiry enquiry : enquiries) {
            System.out.print("\u001B[34m");
            System.out.println("Number is " + index++);
            System.out.println("Camp name: " + enquiry.getCamp().getCampName());
            System.out.println("Query: " + enquiry.getQuery());
            System.out.println("Answer: " + enquiry.getAnswer());
            System.out.print("\u001B[0m");
            System.out.println("---------------------");
        }
    }

    void printSuggestions(){
        List<Suggestion> suggestions = suggestionController.getSuggestionByStaff(staff);
        int index = 0;
        for (Suggestion suggestion : suggestions) {
            System.out.print("\u001B[34m");
            System.out.println("Number is " + index++);
            System.out.println("Camp name: " + suggestion.getCamp().getCampName());
            System.out.println("Suggestion: " + suggestion.getSuggestion());
            System.out.println("IsApproved: " + suggestion.getApproved());
            System.out.print("\u001B[0m");
            System.out.println("---------------------");
        }
    }

    void replyEnquiryAsStaff(){
        printEnquiries();
        List<Enquiry> enquiries = enquiryController.getEnquiryByStaff(staff);

        int index = -1;
        Scanner scanner = new Scanner(System.in);
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Please enter the number of enquiry you want to reply");
            index = scanner.nextInt();
        }
        System.out.println("Please enter the reply");
        scanner.nextLine();
        String reply = scanner.nextLine();

        Enquiry enquiry = enquiries.get(index);

        if(!enquiry.getAnswer().equals("NOT BEEN ANSWERED")){
            System.out.println("Query is answered already");
        } else {
            enquiry.setAnswer(reply);
            System.out.println("Added answer to the query");
        }
    }

    void approveSuggestion(){
        printSuggestions();
        List<Suggestion> suggestions = suggestionController.getSuggestionByStaff(staff);
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= suggestions.size()) {
            System.out.println("Please enter the number you want to approve");
            index = scanner.nextInt();
        }
        Suggestion suggestionToApprove = suggestions.get(index);
        suggestionToApprove.setApproved(true);
        studentController.studentAddOnePoint(suggestionToApprove.getStudent());
        System.out.println("Successfully approved suggestion");


    }

    public void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        staffController.changePassword(staff, newPassword);
    }

    public void generateStaffCampReport(){
        Scanner scanner = new Scanner(System.in);
        boolean attendee = true;
        while (true) {
            System.out.print("Print Attendee?;  0： Yes;     1: No");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                attendee = true;
                break;
            } else if (userInput.equals("1")) {
                attendee = false;
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format 0/1.");
            }
        }

        boolean committee = true;
        while (true) {
            System.out.print("Print Committee?;  0： Yes;     1: No");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                attendee = true;
                break;
            } else if (userInput.equals("1")) {
                attendee = false;
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format 0/1.");
            }
        }

        staffCampReportGeneration.generateReport(staff, attendee, committee);

    }

    public void generatePerformanceReport(){
        committeePerformanceReportGeneration.generateReport(staff);
    }




}
