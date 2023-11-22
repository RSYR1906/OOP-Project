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

public class StaffRepository extends Repository {

    protected final String FILENAME = "staff.csv";

    @Override
    protected Function<Entity, String> formatter() {
        return staff -> {
            Staff staffEntity = (Staff) staff;
            return staffEntity.getName() + SEPARATOR + staffEntity.getEmail() + SEPARATOR + staffEntity.getPassword() + SEPARATOR + staffEntity.getFaculty();
        };
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> new Staff(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
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

    public boolean updateStaffPassword(String staffId, String newPassword) {
        List<Staff> staffMembers = getAllStaff();
        boolean isUpdated = false;

        for (Staff staff : staffMembers) {
            if (staff.getID().equals(staffId)) {
                staff.setPassword(newPassword);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            // Save the updated staff list back to the CSV file
            saveAllStaff(staffMembers);
        }

        return isUpdated;
    }

    private void saveAllStaff(List<Staff> staffMembers) {
        // Logic to write all staff members back to the CSV file
        // Ensure that the CSV file is updated with the new password for the relevant staff member
            FileWriter fileWriter = null;
    try {
        fileWriter = new FileWriter(getFilePath(), false); // false to overwrite the file
        for (Staff staff : staffMembers) {
            String staffData = formatter().apply(staff) + "\n"; // Convert staff object to CSV string
            fileWriter.write(staffData);
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
