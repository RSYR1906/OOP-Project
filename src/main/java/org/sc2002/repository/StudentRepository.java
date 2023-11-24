package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

public class StudentRepository extends Repository{

    public StudentRepository() {
        super();
        setFilePath("student.csv");
    }

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
}
