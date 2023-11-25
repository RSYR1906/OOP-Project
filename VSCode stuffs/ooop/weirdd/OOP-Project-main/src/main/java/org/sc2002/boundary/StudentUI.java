package org.sc2002.boundary;

import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.utils.exception.*;

import java.util.*;

/**
 * The type Student ui.
 */
public class StudentUI implements UI {
    private StudentController studentController;
    private EnqueryController enqueryController;
    private CampController campController;
    private CommitteeController committeeController;

    private Student student;

    /**
     * Instantiates a new Student ui.
     */
    public StudentUI() {
    }

    /**
     * Instantiates a new Student ui.
     *
     * @param studentController the student controller
     * @param student           the student
     * @param enqueryController the enquery controller
     * @param campController    the camp controller
     */
    public StudentUI(StudentController studentController, Student student, EnqueryController enqueryController, CampController campController) {
        this.studentController = studentController;
        this.student = student;
        this.enqueryController = enqueryController;
        this.campController = campController;
    }

    /**
     * Instantiates a new Student ui.
     *
     * @param studentController   the student controller
     * @param enqueryController   the enquery controller
     * @param campController      the camp controller
     * @param committeeController the committee controller
     * @param student             the student
     */
    public StudentUI(StudentController studentController, EnqueryController enqueryController, CampController campController, CommitteeController committeeController, Student student) {
        this.studentController = studentController;
        this.enqueryController = enqueryController;
        this.campController = campController;
        this.committeeController = committeeController;
        this.student = student;
    }


    @Override
    public Object body() throws CampFullException, RegistrationClosedException, EntityNotFoundException, FacultyNotEligibleException, CampConflictException {
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
            System.out.println("9. Reply the enquiry as a committee.");
            System.out.println("10. Give the suggestion to the staff who created the camp as a committee.");
            System.out.println("11. Exit");

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
                    submitEnquiries();
                    break;
                case 4:
                    viewEnquiries();
                    break;
                case 5:
                    editEnquiries();
                    break;
                case 6:
                    deleteEnquery();
                    break;
                case 7:
                    viewCampsRegistered();
                    break;
                case 8:
                    withdrawFromCamps();
                    break;
                case 9:
                    ReplyEnquiryAsCommittee();
                    break;
                case 10:
                    SuggestionToStaff();
                    break;
                case 11:
                    System.out.println("\nExiting. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                    break;
            }
        }

    }

    /**
     * View camps remain map.
     *
     * @return the map
     */
    Map<String, Integer> viewCampsRemain() {
        Map<Camp, Integer> campSlotsMap = studentController.getCampRemainSlots(student);
        Map<String, Integer> campIDSlotsMap = new HashMap<>();
        // 打印首行字段
        System.out.println("\u001B[34mcamps ID,      camps,     RemainSlots\u001B[0m");


        for (Map.Entry<Camp, Integer> entry : campSlotsMap.entrySet()) {

            System.out.println("\u001B[0m" + entry.getKey().getID() + ", " + entry.getKey().getCampName() + ", " + entry.getValue());
            campIDSlotsMap.put(entry.getKey().getID(), entry.getValue());
        }
        return campIDSlotsMap;
    }

    /**
     * Register camps.
     */
    void registerCamps() {
        Map<String, Integer> campSlotsMap = viewCampsRemain();

        Scanner scanner = new Scanner(System.in);
        String campId = "";
        while (!campSlotsMap.containsKey(campId)) {
            System.out.println("Please enter the id of the camp you want to join");
            campId = scanner.nextLine();
            if (!campSlotsMap.containsKey(campId)) System.out.println("Please enter the correct campId");
        }


        System.out.print("Please enter whether to become a member of the camp or committee: 0 members; 1 Committee");
        int input = scanner.nextInt();

        if (input == 0) {
            CampAttendee newCampAttendee = new CampAttendee(campId, student.getID());

            try {
                campController.addStudentToCamp(newCampAttendee);
            } catch (CampConflictException e) {
                System.err.println(e.getMessage());
            }
        } else if (input == 1) {
            Committee committee = new Committee(campId, student.getID());
            try {
                committeeController.addStudentToCommittee(committee);
            } catch (CampConflictException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Submit enquiries.
     */
    void submitEnquiries() {
        Map<String, Integer> viewCampsRemain = viewCampsRemain();

        String campId = "";
        Scanner scanner = new Scanner(System.in);
        while (!viewCampsRemain.containsKey(campId)) {
            System.out.println("Please enter the id of the camp you want to enquiry");
            campId = scanner.nextLine();
            if (!viewCampsRemain.containsKey(campId)) System.out.println("Please enter the correct camp name");
        }


        System.out.print("Please enter query: ");
        String query = scanner.nextLine();
        Enquery enquery = new Enquery(query, student.getID(), campId);
        studentController.addEnquiry(enquery);

        // 现在你可以使用用户输入的campId和query进行其他操作
        System.out.println("The campId you entered is: " + campId);
        System.out.println("The query you entered is: " + query);
    }

    /**
     * View enquiries list.
     *
     * @return the list
     */
    List<Enquery> viewEnquiries() {
        List<Enquery> enqueryByStudent = enqueryController.getEnqueryByStudent(student);
        int index = 0;
        for (Enquery enquery : enqueryByStudent) {

            System.out.print("\u001B[34m");
            System.out.println("Number is " + index++);
            System.out.println("Query: " + enquery.getQuery());
//            System.out.println("Answer: " + enquery.getAnswer());
            System.out.println("Camp name: " + campController.getCampById(enquery.getCampId()).getCampName());
//            System.out.println("Suggestion: " + enquery.getSuggestion());
            System.out.println("Reply: " + enquery.getReply());
//            System.out.println("Is Approve: " + enquery.getIsApprove());
            System.out.print("\u001B[0m");
            System.out.println("---------------------");
        }
        return enqueryByStudent;
    }

    /**
     * Edit enquiries.
     */
    void editEnquiries() {
        List<Enquery> enqueries = viewEnquiries();
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enqueries.size()) {
            System.out.println("Please enter the number you want to edit");
            index = scanner.nextInt();
        }
        System.out.println("Please enter the new enquiry");
        scanner.nextLine();
        String quiry = scanner.nextLine();
        Enquery enquery = enqueries.get(index);
        enquery.setQuery(quiry);
        enqueryController.editEnquery(enquery);
    }

    /**
     * Delete enquery.
     */
    void deleteEnquery() {
        List<Enquery> enqueries = viewEnquiries();
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enqueries.size()) {
            System.out.println("Please enter the number you want to delete the inquiry from");
            index = scanner.nextInt();
        }
        enqueryController.deleteEnquery(enqueries.get(index).getID());
    }

    /**
     * View camps registered set.
     *
     * @return the set
     */
    Set<String> viewCampsRegistered() {
        Set<String> campStudentRegistered = new HashSet<>();
        List<String> campByStudent = campController.getCampByStudent(student);
        List<String> committeeByStudent = committeeController.getCommitteeByStudent(student.getID());
        Map<String, String> campIdCampName = campController.getCampIdCampName();
        System.out.print("\u001B[34m");
        System.out.println("Camps attended by the student:");
        for (String campId : campByStudent) {
            String campName = campIdCampName.get(campId);
            System.out.println("Camp ID: " + campId + ", Camp Name: " + campName);
            campStudentRegistered.add(campId);
        }
        if (committeeByStudent.size() > 0) {
            System.out.println("\nCommittees participated by the student:");
            for (String committeeId : committeeByStudent) {
                String campName = campIdCampName.get(committeeId);
                System.out.println("Committee ID: " + committeeId + ", Camp Name: " + campName);

            }
        }
        System.out.print("\u001B[0m");
        System.out.println("---------------------");

        return campStudentRegistered;
    }

    /**
     * Withdraw from camps.
     */
    void withdrawFromCamps() {
        Set<String> campStudentRegistered = viewCampsRegistered();
        Scanner scanner = new Scanner(System.in);
        String campId = "";
        while (!campStudentRegistered.contains(campId)) {
            System.out.println("Please enter the id of the camp as a member you want to withdraw");
            campId = scanner.nextLine();
            if (!campStudentRegistered.contains(campId)) System.out.println("Please enter the correct campId");
        }
        CampAttendee campAttendee = new CampAttendee(campId, student.getID(), true);
        campController.withDrawByStudent(campAttendee);

    }


    void ReplyEnquiryAsCommittee() {
        List<String> committee = committeeController.getCommitteeByStudent(student.getID());
        if (committee.size() == 0) {
            System.out.println("You did not sign up for the committee, no permission");
            return;
        }
        String campId = committee.get(0);
        List<Enquery> enqueryByCommitee = enqueryController.getEnqueryByCampId(campId);
        int index = 0;
        for (Enquery enquery : enqueryByCommitee) {

            System.out.print("\u001B[34m");
            System.out.println("Number is " + index++);
            System.out.println("Query: " + enquery.getQuery());
//            System.out.println("Answer: " + enquery.getAnswer());
            System.out.println("Camp name: " + campController.getCampById(enquery.getCampId()).getCampName());
//            System.out.println("Suggestion: " + enquery.getSuggestion());
            System.out.println("Reply: " + enquery.getReply());
//            System.out.println("Is Approve: " + enquery.getIsApprove());
            System.out.print("\u001B[0m");
            System.out.println("---------------------");
        }

        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enqueryByCommitee.size()) {
            System.out.println("Please enter the number of enquiry you want to reply");
            index = scanner.nextInt();
        }
        System.out.println("Please enter the reply");
        scanner.nextLine();
        String reply = scanner.nextLine();

        if (enqueryByCommitee.get(index).getReply().equals("null")) {
            enqueryByCommitee.get(index).setReply(reply);
            Enquery enquery = enqueryByCommitee.get(index);
            enqueryController.editEnquery(enquery);
            studentController.studentAddOneScore(student);

        } else {
            Enquery enquery = new Enquery(enqueryByCommitee.get(index).getQuery(), reply, student.getID(), campId);
            enqueryController.addEnquery(enquery);
            studentController.studentAddOneScore(student);
        }


    }

    void SuggestionToStaff() {
        List<String> committee = committeeController.getCommitteeByStudent(student.getID());
        if (committee.size() == 0) {
            System.out.println("You did not sign up for the committee, no permission");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String campId = committee.get(0);
        System.out.println("Please enter the suggestion as a committee");
        String suggestion = scanner.nextLine();
        String staffName = campController.getCampById(campId).getStaffInChargeID();
        Enquery enquery = new Enquery(suggestion, student.getID(), campId, staffName,false);

        enqueryController.addEnquery(enquery);
        studentController.studentAddOneScore(student);
    }
}
