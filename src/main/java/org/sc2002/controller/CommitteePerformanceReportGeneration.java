package org.sc2002.controller;

import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CommitteePerformanceReportGeneration implements ReportGeneration {

    StudentController studentController;

    public CommitteePerformanceReportGeneration(StudentController studentController) {
        this.studentController = studentController;
    }

    public void generateReport(Staff staff) {
        List<Student> committeeMembers = studentController.getAllStudents().stream().filter(student -> student.isCampCommitteeMember()).collect(Collectors.toList());

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        String filename = "report-committee-perf-" + staff.getID() + "-" + currentTime + ".txt";


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("COMMITTEE REGISTERED\n");
            writer.write("-----------------------------\n");
            writer.write("ID\tNAME\tFACULTY\tPOINTS\n");
            writer.write("-----------------------------\n");
            for (Student committeeMember : committeeMembers) {
                writer.write(committeeMember.getID() + "\t" + committeeMember.getName() + "\t" + committeeMember.getFaculty() + "\t" + committeeMember.getPoint() + "\n");
            }
            writer.write("-----------------------------\n");
            System.out.println("SUCCESSFULLY GENERATED REPORT");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to generate report");
        }

    }
}
