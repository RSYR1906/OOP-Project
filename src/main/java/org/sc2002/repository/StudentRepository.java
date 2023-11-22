package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

public class StudentRepository extends Repository{

    protected final String FILENAME = "student.csv";

    @Override
    protected Function<Entity, String> formatter() {
        return student -> {
            Student studentEntity = (Student) student;
            return studentEntity.getName() + SEPARATOR + studentEntity.getEmail() + SEPARATOR + studentEntity.getPassword() + SEPARATOR  + studentEntity.getFaculty();
        };
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> new Student(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
    }

    @Override
    public String getFilePath() {

        return FILENAME;
    }

    /**
     * Gets the list of students stored in the repository
     *
     * @return the list of students
     */
    public List<Student> getAllStudents() {
        List<Entity> entities = super.getAll();
        List<Student> students = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Student) {
                students.add((Student) entity);
            }
        }
        return students;
    }

    /**
     * Gets a student by ID
     *
     * @param entityID the ID of the student to find
     * @return the student with the given ID
     */
    public Student getStudentByID(String entityID) throws EntityNotFoundException {
        Student student;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Student) {
            student = (Student) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return student;
    }

     public boolean updateStudentPassword(String studentId, String newPassword) {
        List<Student> students = getAllStudents();
        boolean isUpdated = false;

        for (Student student : students) {
            if (student.getID().equals(studentId)) {
                student.setPassword(newPassword);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            // Save the updated student list back to the CSV file
            saveAllStudent(students);
        }

        return isUpdated;
    }

    private void saveAllStudent(List<Student> students) {
        // Logic to write all students back to the CSV file
        // Ensure that the CSV file is updated with the new password for the students
            FileWriter fileWriter = null;
    try {
        fileWriter = new FileWriter(getFilePath(), false); // false to overwrite the file
        for (Student student : students) {
            String studentData = formatter().apply(student) + "\n"; // Convert student object to CSV string
            fileWriter.write(studentData);
        }
    } catch (IOException e) {
        e.printStackTrace();
        // Handle exceptions (e.g., log them, notify the user)
    } finally {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception during file close operation
        }
    }

    }

}

