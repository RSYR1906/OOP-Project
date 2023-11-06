package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Staff;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

public class StaffRepository extends Repository {

    protected final String FILENAME = "staff.csv";

    // SINGLETON PATTERN
    // Private static variable of the same class that is the only instance of the class.
    private static StaffRepository instance;

    // Private constructor to restrict instantiation of the class from other classes.
    private StaffRepository() {}

    // Public static method that returns the instance of the class.
    public static synchronized StaffRepository getInstance() {
        if (instance == null) {
            instance = new StaffRepository();
        }
        return instance;
    }

    @Override
    protected Function<Entity, String> formatter() {
        return staff -> {
            Staff staffEntity = (Staff) staff;
            return staffEntity.getName() + SEPARATOR + staffEntity.getEmail() + SEPARATOR + staffEntity.getFaculty().name();
        };
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> new Staff(fields[0].trim(), fields[1].trim(), fields[2].trim());
    }

    @Override
    public String getFilePath() {
        return FILENAME;
    }

    // Methods specific to Staff entity
    // Implement methods for handling staff-related functionality based on your requirements.
    // For example, methods for creating, editing, and deleting camps, replying to inquiries, generating reports, etc.

    /**
     * Gets the list of staff members stored in the repository
     *
     * @return the list of staff members
     */
    public List<Staff> getAllStaff() {
        List<Entity> entities = super.getAll();
        List<Staff> staffMembers = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Staff) {
                staffMembers.add((Staff) entity);
            }
        }
        return staffMembers;
    }

    /**
     * Gets a staff member by ID
     *
     * @param entityID the ID of the staff member to find
     * @return the staff member with the given ID
     */
    public Staff getStaffByID(String entityID) throws EntityNotFoundException {
        Staff staff;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Staff) {
            staff = (Staff) entity;
        } 
        else {
            throw new EntityNotFoundException();
        }
        return staff;
    }
}
