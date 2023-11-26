package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommitteeCampReportGeneration implements ReportGeneration{

    public void generateReport(Student student, Boolean attendee, Boolean committee) {

        Camp camp = student.getCommitteeMemberCamp();

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        String filename = "report-" + student.getID() + "-" + currentTime + ".txt";

        System.out.println("CREATING REPORT...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

                printCamp(camp, writer);

                ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
                ArrayList<Student> committeeRegistered = camp.getCommitteeRegistered();

                if(attendee){
                    writer.write("STUDENT ATTENDEES REGISTERED\n");
                    writer.write("-----------------------------\n");
                    writer.write("ID\tNAME\tFACULTY\n");
                    writer.write("-----------------------------\n");
                    for (Student studentAttendee : studentsRegistered) {
                        writer.write(studentAttendee.getID() + "\t" + studentAttendee.getName() + "\t" + studentAttendee.getFaculty() + "\n");
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
