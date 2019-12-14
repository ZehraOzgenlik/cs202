package tr.edu.ozyegin.cs202.service.model;

public class Patient extends User {

    public Patient(String id, String firstName, String lastName, String password, UserType userType) {
        super(id, firstName, lastName, password, userType);
    }

    public Patient() {
    }
}
