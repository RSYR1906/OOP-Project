package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffCampReportGeneration implements ReportGeneration{

    CampController campController;

    public StaffCampReportGeneration(CampController campController) {
        this.campController = campController;
    }

    public void generateReport(Staff staff, Boolean attendee, Boolean committee) {
        List<Camp> createdCamps = campController.getAllCamps().stream()
                .filter(camp -> camp.getStaffInChargeID().equals(staff.getID()))
                .collect(Collectors.toList());

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        String filename = "report-" + staff.getID() + "-" + currentTime + ".txt";

        System.out.println("CREATING REPORT...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Camp camp : createdCamps) {
                printCamp(camp, writer);

                ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
                ArrayList<Student> committeeRegistered = camp.getCommitteeRegistered();

                if(attendee){
                    writer.write("STUDENT ATTENDEES REGISTERED\n");
                    writer.write("-----------------------------\n");
                    writer.write("ID\tNAME\tFACULTY\n");
                    writer.write("-----------------------------\n");
                    for (Student student : studentsRegistered) {
                        writer.write(student.getID() + "\t" + student.getName() + "\t" + student.getFaculty() + "\n");
                    }
                    writer.write("\n");
                    writer.write("////////////////////////////////\n");
                    writer.write("\n");
                }


                if(committee){
                    writer.write("COMMITTEE REGISTERED\n");
                    writer.write("-----------------------------\n");
                    writer.write("ID\tNAME\tFACULTY\n");
                    writer.write("-----------------------------\n");
                    for (Student committeeMember : committeeRegistered) {
                        writer.write(committeeMember.getID() + "\t" + committeeMember.getName() + "\t" + committeeMember.getFaculty() + "\n");
                    }
                }
                writer.write("-----------------------------\n");
            }

            System.out.println("SUCCESSFULLY GENERATED REPORT");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to generate report");
        }
    }

    public static void printCamp(Camp camp, BufferedWriter writer) throws IOException {
        writer.write("Camp Name: " + camp.getCampName() + "\n");
        writer.write("Camp Start Date: " + camp.getCampStartDate() + "\n");
        writer.write("Registration closing date: " + camp.getCampEndDate() + "\n");
        writer.write("Camp Registration End Date: " + camp.getCampRegistrationEndDate() + "\n");
        writer.write("User group: " + camp.getUserGroupOpenTo() + "\n");
        writer.write("Location: " + camp.getLocation() + "\n");
        writer.write("Total Slots: " + camp.getTotalSlots() + "\n");
        writer.write("Camp Committee Slots: " + camp.getCampCommitteeSlots() + "\n");
        writer.write("Description: " + camp.getDescription() + "\n");
        writer.write("Staff in charge: " + camp.getStaffInChargeID() + "\n");
        writer.write("Camp is " + (camp.getVisibilityToStudent() ? "visible" : "invisible") + "\n");
        writer.write("------------------------------------\n");
    }
}
