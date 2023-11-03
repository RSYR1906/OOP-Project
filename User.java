public interface User {
    // Get the user ID, which is the part before @ in the email address.
    String getUserId();

    // Get the user's email address.
    String getEmail();

    // Get the user's password.
    String getPassword();

    // Change the user's password.
    void changePassword(String newPassword);

    // Get the user's faculty information.
    String getFaculty();
}
