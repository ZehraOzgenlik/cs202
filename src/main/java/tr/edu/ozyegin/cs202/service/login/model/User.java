package tr.edu.ozyegin.cs202.service.login.model;

public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String password;
    public UserType userType;

    public User(String id, String firstName, String lastName, String password, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }

    public User() {
    }
}
