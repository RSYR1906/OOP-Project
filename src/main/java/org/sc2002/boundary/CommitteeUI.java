package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.EnquiryController;
import org.sc2002.controller.StudentController;
import org.sc2002.controller.SuggestionController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Enquiry;
import org.sc2002.entity.Student;
import org.sc2002.entity.Suggestion;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommitteeUI implements UI{

    private Student student;
    private StudentController studentController;
    private CampController campController;
    private EnquiryController enquiryController;
    private SuggestionController suggestionController;

    public CommitteeUI(Student student, StudentController studentController, CampController campController, EnquiryController enquiryController, SuggestionController suggestionController) {
        this.student = student;
        this.studentController = studentController;
        this.campController = campController;
        this.enquiryController = enquiryController;
        this.suggestionController = suggestionController;
    }

    @Override
    public Object body() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[36m\nCOMMITTEE MENU:\u001B[0m");
        System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
        System.out.println("1. Reply the enquiry as a committee.");
        System.out.println("2. Give the suggestion to the staff who created the camp as a committee.");
        System.out.println("3. View my suggestions");
        System.out.println("4. Edit my suggestions");
        System.out.println("5. Delete my suggestions");
        System.out.println("6. Go back to student main menu");
        System.out.print("\nYour choice (1-4): ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                replyEnquiryAsCommittee();
                break;
            case 2:
                submitSuggestion();
                break;
            case 3:
                printSuggestions();
                break;
            case 4:
                editSuggestion();
                break;
            case 5:
                deleteSuggestion();
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        return null;
    }

    void replyEnquiryAsCommittee() {
        if(!student.isCampCommitteeMember()){
            System.out.println("You did not sign up for the committee, no permission");
            return;
        }

        List<Enquiry> enquiries = enquiryController.getEnquiryByCamp(student.getCommitteeMemberCamp());
        printEnquiries(enquiries);

        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enquiries.size()) {
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
            studentController.studentAddOnePoint(student);
            System.out.println("Added answer to the query");
        }
    }

    void printEnquiries(List<Enquiry> enquiries){
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

    void printCommitteeCamp(){
        Camp camp = student.getCommitteeMemberCamp();
        printCamp(camp);
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

    public void printSuggestions(){
        List<Suggestion> suggestions = suggestionController.getSuggestionByStudent(student);
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

    void submitSuggestion(){
        printCommitteeCamp();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter suggestion: ");
        String suggestionContent = scanner.nextLine();

        try{
            Suggestion suggestion = new Suggestion(student, student.getCommitteeMemberCamp(), suggestionContent);
            suggestionController.addSuggestion(suggestion);
        } catch (Exception e){
            System.out.println("Failed to submit: " + e.getMessage());
        }
    }

    void editSuggestion(){
        printSuggestions();
        List<Suggestion> suggestions = suggestionController.getSuggestionByStudent(student);
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= suggestions.size()) {
            System.out.println("Please enter the number you want to edit");
            index = scanner.nextInt();
        }
        System.out.println("Please enter the new suggestion");
        scanner.nextLine();
        String suggestionContent = scanner.nextLine();
        Suggestion suggestionToEdit = suggestions.get(index);
        suggestionToEdit.setSuggestion(suggestionContent);
        System.out.println("Successfully edited suggestion");
    }

    void deleteSuggestion(){
        printSuggestions();
        List<Suggestion> suggestions = suggestionController.getSuggestionByStudent(student);
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= suggestions.size()) {
            System.out.println("Please enter the number you want to delete the suggestion from");
            index = scanner.nextInt();
        }
        suggestionController.deleteSuggestion(suggestions.get(index).getID());
        System.out.println("Successfully deleted suggestion");
    }
}
