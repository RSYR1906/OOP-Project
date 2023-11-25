package org.sc2002.boundary;

import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Staff ui.
 */
public class StaffUI implements UI {
    private StaffController staffController;
    private CampController campController;
    private UserController userController;
    private CommitteeController committeeController;
    private CampRepository campRepository;
    private StudentController studentController;
    private User user;

    /**
     * Instantiates a new Staff ui.
     */
    public StaffUI() {
    }

    /**
     * Instantiates a new Staff ui.
     *
     * @param staffController     the staff controller
     * @param campController      the camp controller
     * @param userController      the user controller
     * @param committeeController the committee controller
     * @param campRepository      the camp repository
     * @param user                the user
     */
    public StaffUI(StaffController staffController, CampController campController, UserController userController, CommitteeController committeeController, CampRepository campRepository, StudentController studentController, User user) {
        this.staffController = staffController;
        this.committeeController = committeeController;
        this.userController = userController;
        this.campController = campController;
        this.campRepository = campRepository;
        this.studentController = studentController;
        this.user = user;
    }


    @Override
    public Object body() {
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
            System.out.println("9. exit");

            System.out.print("\nYour choice (1-9): ");
            int choice = 0;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    changeCamp(user);
                    break;
                case 2:
                    changeVisibility();
                    break;
                case 3:
                    viewAllCamp();
                    break;
                case 4:
                    viewAllMyCreateCamp(user);
                    break;
                case 5:
                    viewAndReply(user);
                    break;
                case 6:
                    viewAndApproveSuggestion(user);
                    break;
                case 7:
                    GenerateStudentsReport(user);
                    break;
                case 8:
                    GeneratePerformanceReport(user);
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

    private void GeneratePerformanceReport(User user) {
        System.out.println("now create performanceReport");
        if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            try {
                committeeController.createReport(staff);
                System.out.println("creare performanceReport success");
            } catch (EntityNotFoundException e) {
                System.out.println("create performanceReport fail");
            }
        } else {
            System.out.println("No permission to create, create fail");
        }

    }

    private void GenerateStudentsReport(User user) {
        System.out.println("now create studentReport");
        if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            try {
                staffController.createReport(staff);
                System.out.println("create studentReport success");
            } catch (EntityNotFoundException e) {
                System.out.println("create studentReport fail");
            }
        } else {
            System.out.println("No permission to create, create fail");
        }

    }

    private void viewAndApproveSuggestion(User user) {
        if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            try {
                List<Enquery> suggestions = staffController.getSuggestions(staff);
                System.out.println("now show all the suggestions");
                int index = 0;
                for (Enquery suggestion : suggestions) {
                    System.out.println("Number:" + index++ + " , campName:" + campController.getCampById(suggestion.getCampId()).getCampName() + " , suggestion:" + suggestion.getSuggestion());
                }
                System.out.println("if you want to approve suggestions to changes to camp details from camp committee please enter 1");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if (s.equals("1")) {
                    System.out.println("please enter the number which suggestion you want to approve");
                    index = scanner.nextInt();

                    Enquery suggestion = suggestions.get(index);
                    suggestion.setIsApprove(true);
                    staffController.approveSuggestions(suggestion);
                    studentController.studentAddOneScore(suggestion.getSuggestStudent());
                    System.out.println("approve suggestion success");
                } else {
                    System.out.println("enter error");
                }

            } catch (EntityNotFoundException e) {
                System.out.println("No permission, delete fail");
            }

        } else {
            System.out.println("No permission to view");
        }
    }

    private void viewAndReply(User user) {
        if (userController.getUserRole(user).equals("Student")) {
            Student student = (Student) user;
            Map<String, String> replyMap = committeeController.viewReply(student);
            System.out.println("now show the Reply");
            for (Map.Entry<String, String> entry : replyMap.entrySet()) {
                System.out.println("campName:" + entry.getKey() + " , reply:" + entry.getValue());
            }
            System.out.println("if you want to reply to enquiries from students to the camp(s) you have created please enter 1 else 0");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            if (s.equals("1")) {
                System.out.println("please enter you want to reply campName");
                String campName = scanner.nextLine();
                System.out.println("please enter you reply");
                String reply = scanner.nextLine();
                try {
                    committeeController.editReply(student, campName, reply);
                } catch (EntityNotFoundException e) {
                    System.out.println("reply fail");
                }
            } else {
                System.out.println("exit");
            }

            return;

        } else if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            System.out.println("please enter the campName");
            Scanner scanner = new Scanner(System.in);
            String campName = scanner.nextLine();
            Map<String, String> replyMap = staffController.viewReply(staff, campName);
            System.out.println("now show the Reply");
            for (Map.Entry<String, String> entry : replyMap.entrySet()) {
                System.out.println("campName:" + entry.getKey() + " , reply:" + entry.getValue());
            }
            System.out.println("if you want to reply to enquiries from students to the camp(s) you have created please enter 1 else 0");
            String s = scanner.nextLine();
            if (s.equals("1")) {
                System.out.println("please enter you reply");
                String reply = scanner.nextLine();
                try {
                    staffController.editReply(staff, campName, reply);
                } catch (EntityNotFoundException e) {
                    System.out.println("reply fail");
                }
            } else {
                System.out.println("exit");
            }

            return;
        }

    }

    private List<Camp> viewAllMyCreateCamp(User user) {
        List<Camp> camps = null;
        if (userController.getUserRole(user).equals("Staff Member")) {
            try {
                Staff staff = (Staff) user;
                camps = staffController.viewAllCampsICreated(staff.getID());
                viewAllCamp(camps);
            } catch (EntityNotFoundException e) {
                System.out.println("view all my create camp fail");
            }
        }
        return camps;
    }

    private void changeVisibility() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the camp's id");
        String campId = scanner.nextLine();
        if (userController.getUserRole(user).equals("Staff Member")) {
            try {
                staffController.changeVisibility((Staff) user, campId);
            } catch (EntityNotFoundException e) {
                System.out.println("change Visibilty fail");
            }
        } else {
            System.out.println("No permission to change, change fail");
        }

    }

    private static boolean isValidDate(String date) {
        // Use a simple regex to validate the date format (YYYY-MM-DD)
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    /**
     * Create camp.
     *
     * @param user the user
     */
    public void createCamp(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Camp Creator!");
        System.out.println("Please enter the following details:");

        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();

        LocalDate campStartDate = null;
        while (true) {
            System.out.print("Camp Start Date (format: YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            if (isValidDate(dateString)) {
                campStartDate = LocalDate.parse(dateString);
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }

        LocalDate campEndDate = null;
        while (true) {
            System.out.print("Camp End Date (format: YYYY-MM-DD): ");
            String closingDateString = scanner.nextLine();
            if (isValidDate(closingDateString)) {
                campEndDate = LocalDate.parse(closingDateString);
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }

        LocalDate campRegistrationEndDate = null;
        while (true) {
            System.out.print("Camp Registration End Date (format: YYYY-MM-DD): ");
            String closingDateString = scanner.nextLine();
            if (isValidDate(closingDateString)) {
                campRegistrationEndDate = LocalDate.parse(closingDateString);
                break;
            } else {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }
        Faculty userGroupOpenTo;
        while (true) {
            System.out.print("User group (own school or whole NTU): Please enter 0: own school;     1: whole NTU");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                userGroupOpenTo = user.getFaculty();
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
        String staffInCharge = user.getID();

        boolean visibilityToStudent = true;
        while (true) {
            System.out.print("Is the current campsite visible to students;  0: invisible;     1: visible");
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
            Camp camp = new Camp();
            camp.setCampName(campName);
            camp.setDescription(description);
            camp.setCampStartDate(campStartDate);
            camp.setCampEndDate(campEndDate);
            camp.setCampRegistrationEndDate(campRegistrationEndDate);
            camp.setUserGroupOpenTo(userGroupOpenTo);
            camp.setLocation(location);
            camp.setTotalSlots(totalSlots);
            camp.setCampCommitteeSlots(committeeSlots);
            camp.setStaffInChargeID(staffInCharge);
            camp.setVisibilityToStudent(visibilityToStudent);
            staffController.createCamp(camp);
        } catch (Exception e) {
            System.out.println("create error");
            System.out.println(e.getMessage());
        }
        // Print the entered details

        System.out.println("\nCamp created successfully! Details:");
        System.out.println("\u001B[34mCamp Name:\u001B[0m " + campName);
        System.out.println("\u001B[34mCamp Start Date:\u001B[0m " + campStartDate);
        System.out.println("\u001B[34mRegistration closing date:\u001B[0m " + campEndDate);
        System.out.println("\u001B[34mCamp Registration End Date:\u001B[0m " + campRegistrationEndDate);
        System.out.println("\u001B[34mUser group:\u001B[0m " + userGroupOpenTo);
        System.out.println("\u001B[34mLocation:\u001B[0m " + location);
        System.out.println("\u001B[34mTotal Slots:\u001B[0m " + totalSlots);
        System.out.println("\u001B[34mCamp Committee Slots:\u001B[0m " + committeeSlots);
        System.out.println("\u001B[34mDescription:\u001B[0m " + description);
        System.out.println("\u001B[34mStaff in charge:\u001B[0m " + staffInCharge);
        System.out.print("\u001B[34mCamp is \u001B[0m ");
        System.out.println(visibilityToStudent ? "visible" : "invisible");

    }

    /**
     * Edit camp.
     *
     * @param user the user
     */
    public void editCamp(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want to edit camp you should enter the following details!");
        System.out.println("Please enter the following details:");

        System.out.print("Camp Name: ");
        String campName = scanner.nextLine();
        String campId = null;
        LocalDate campStartDate = null;
        LocalDate campEndDate = null;
        LocalDate campRegistrationEndDate = null;
        List<Camp> allCamps = campRepository.getAllCamps();
        for (Camp camp : allCamps) {
            if (camp.getCampName().equals(campName)) {
                campId = camp.getID();
                campStartDate = camp.getCampStartDate();
                campEndDate = camp.getCampEndDate();
                campRegistrationEndDate = camp.getCampRegistrationEndDate();
            }
        }
        if (campStartDate == null || campEndDate == null || campRegistrationEndDate == null) {
            System.out.println("the camp is not exist");
            return;
        }

        Faculty userGroupOpenTo;
        while (true) {
            System.out.print("User group (own school or whole NTU): Please enter 0: own school;     1: whole NTU");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                userGroupOpenTo = user.getFaculty();
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
        String staffInCharge = user.getID();

        boolean visibilityToStudent = true;
        while (true) {
            System.out.print("Is the current campsite visible to students;  0: invisible;     1: visible");
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

        Camp camp = new Camp(campId, campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, committeeSlots, staffInCharge, visibilityToStudent);
        campController.editCamp(camp);

    }

    /**
     * Delete camp.
     *
     * @param user the user
     */
    public void deleteCamp(User user) {
        List<Camp> camps = viewAllCamp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want delete camp, you should enter the following details!");

        int campId = -1;

        while (campId < 0 || campId >= camps.size()) {
            System.out.println("please enter the right camp's id");
            if (scanner.hasNextInt()) {
                campId = scanner.nextInt();
                // 处理用户输入
            } else {
                System.out.println("Please enter an integer.");

            }

        }
        Camp deleteCamp = camps.get(campId);
        String staffInChargeID = deleteCamp.getStaffInChargeID();
        if (staffInChargeID.equals(user.getID())) {
            campController.deleteCamp(deleteCamp.getID());
            System.out.println("success delete");
            viewAllCamp();
        } else {
            System.out.println("No permission to delete, delete fail");
        }

    }

    /**
     * Change camp.
     *
     * @param user the user
     */
    public void changeCamp(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nYour choice (1-3): ");
        System.out.print("\n1. create             2.edit                             3.delete");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                createCamp(user);
                break;
            case 2:
                editCamp(user);
                break;
            case 3:
                deleteCamp(user);
                break;
            default:
                System.out.println();
                break;
        }


    }


    /**
     * View all camp list.
     *
     * @return the list
     */
    public List<Camp> viewAllCamp() {
        System.out.println("there are all camps");
        List<Camp> camps = staffController.viewAllCamps();
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
            System.out.println("\u001B[34mCamp Id:\u001B[0m " + index++);
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
        return camps;
    }

    /**
     * View all camp.
     *
     * @param camps the camps
     */
    public void viewAllCamp(List<Camp> camps) {
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
            System.out.println("\nCamp created successfully! Details:");
            System.out.println("\u001B[34mCamp Id:\u001B[0m " + campId);
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
}