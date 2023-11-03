package main.controller.account.user;

import main.model.user.User;
import main.model.user.UserType;
import main.repository.user.StaffRepository;
import main.repository.user.StudentRepository;
import main.utils.exception.ModelNotFoundException;

/**
 * A class that provides a utility for finding users in the database.
 */
public class UserFinder {
    /**
     * Finds the user with the specified ID.
     *
     * @param userID the ID of the user to be found
     * @return the user with the specified ID
     * @throws ModelNotFoundException if the user is not found
     */
    private static User findStudent(String userID) throws ModelNotFoundException {
        return StudentRepository.getInstance().getByID(userID);
    }

    /**
     * Finds the faculty with the specified ID.
     *
     * @param userID the ID of the faculty to be found
     * @return the faculty with the specified ID
     * @throws ModelNotFoundException if the faculty is not found
     */
    private static User findStaff(String userID) throws ModelNotFoundException {
        return StaffRepository.getInstance().getByID(userID);
    }

    /**
     * Finds the user with the specified ID.
     *
     * @param userID   the ID of the user to be found
     * @param userType the type of the user to be found
     * @return the user with the specified ID
     * @throws ModelNotFoundException if the user is not found
     */
    public static User findUser(String userID, UserType userType) throws ModelNotFoundException {
        return switch (userType) {
            case STUDENT -> findStudent(userID);
            case STAFF -> findStaff(userID);
        };
    }
}
