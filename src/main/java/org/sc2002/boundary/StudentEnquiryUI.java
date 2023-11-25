package org.sc2002.boundary;

import org.sc2002.controller.CampController;
import org.sc2002.controller.EnquiryController;
import org.sc2002.controller.StudentController;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Enquiry;
import org.sc2002.entity.Student;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentEnquiryUI implements UI {

    private Student student;
    private StudentController studentController;
    private CampController campController;
    private EnquiryController enquiryController;

    public StudentEnquiryUI(Student student, StudentController studentController, CampController campController, EnquiryController enquiryController) {
        this.student = student;
        this.studentController = studentController;
        this.campController = campController;
        this.enquiryController = enquiryController;
    }

    @Override
    public Object body() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[36m\nSTUDENT ENQUIRIES:\u001B[0m");
        System.out.println("\u001B[36m\nPlease enter your choice to continue:\u001B[0m");
        System.out.println("1. Submit enquiries regarding a camp");
        System.out.println("2. View your enquiries");
        System.out.println("3. Edit your enquiries");
        System.out.println("4. Delete your enquiries");
        System.out.println("5. Go back to student main menu");
        System.out.print("\nYour choice (1-4): ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                submitEnquiries();
                break;
            case 2:
                printEnquiries();
                break;
            case 3:
                editEnquiries();
                break;
            case 4:
                deleteEnquiry();
                break;
            case 5:
                break;
            default:
                System.out.println("\u001B[31mInvalid choice. Please enter a valid option.\u001B[0m");
                break;
        }
        return null;
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

    void submitEnquiries(){
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

        try{
            Camp camp = campController.getCamp(campId);
            Enquiry enquiry = new Enquiry(student, camp, query);
            enquiryController.addEnquiry(enquiry);
            System.out.println("Successfully created enquiry on : " + camp.getCampName());
        } catch (Exception e){
            System.out.println("Failed to register: " + e.getMessage());
        }
    }

    void printEnquiries(){
        List<Enquiry> enquiries = enquiryController.getEnquiryByStudent(student);
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

    void editEnquiries(){
        printEnquiries();
        List<Enquiry> enquiries = enquiryController.getEnquiryByStudent(student);
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enquiries.size()) {
            System.out.println("Please enter the number you want to edit");
            index = scanner.nextInt();
        }
        System.out.println("Please enter the new enquiry");
        String query = scanner.nextLine();
        Enquiry enquiryToEdit = enquiries.get(index);
        enquiryToEdit.setQuery(query);
        enquiryController.editEnquiry(enquiryToEdit);
        System.out.println("Successfully edited enquiry");
    }

    void deleteEnquiry(){
        printEnquiries();
        List<Enquiry> enquiries = enquiryController.getEnquiryByStudent(student);
        int index = -1;
        Scanner scanner = new Scanner(System.in);
        while (index < 0 || index >= enquiries.size()) {
            System.out.println("Please enter the number you want to delete the inquiry from");
            index = scanner.nextInt();
        }
        enquiryController.deleteEnquiry(enquiries.get(index).getID());
        System.out.println("Successfully deleted enquiry");
    }
}
