package tr.edu.ozyegin.cs202.util;

public class Validator {

    public static void validateUserId(String userId) throws Exception {
        if (userId == null || userId.length() != 11) {
            throw new Exception("User id should be 11 character!");
        }
    }

    public static void validateFirstName(String firstName) throws Exception {
        if (firstName == null || firstName.isEmpty() || firstName.length() < 2 || firstName.length() > 255) {
            throw new Exception("First name should be 2 to 255 character long!");
        }
    }

    public static void validateLastName(String lastName) throws Exception {
        if (lastName == null || lastName.isEmpty() || lastName.length() < 2 || lastName.length() > 255) {
            throw new Exception("Last name should be 2 to 255 character long!");
        }
    }

    public static void validatePassword(String password) throws Exception {
        if (password == null || password.isEmpty() || password.length() < 4 || password.length() > 255) {
            throw new Exception("Password should be 4 to 255 character long!");
        }
    }
}
