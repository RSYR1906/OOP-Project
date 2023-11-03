package main.model.user;

import main.controller.account.password.PasswordHashManager;

/**

 A factory class for creating User objects based on the given parameters.
 */
public class UserFactory {
    /**
     * Creates a new User object based on the given parameters.
     * @param userID    The user's ID.
     * @param userType  The type of user to be created (student,staff).
     * @param password  The user's password in plaintext.
     * @param name      The user's name.
     * @param email     The user's email address.
     * @return          A new User object of the specified type.
     */
    public static User create(UserType userType,String userID, String password, String name, String email) {
        String hashedPassword = PasswordHashManager.hashPassword(password);
        return switch (userType) {
            case STUDENT -> new Student(userID, name, email, hashedPassword);
            case FACULTY -> new Staff(userID, name, email, hashedPassword);
        };
    }
}
