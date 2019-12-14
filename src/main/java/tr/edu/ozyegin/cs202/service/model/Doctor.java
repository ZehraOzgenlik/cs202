package tr.edu.ozyegin.cs202.service.model;

import java.util.List;

public class Doctor extends User {
    public List<Department> departments;

    public Doctor(String id, String firstName, String lastName, String password, UserType userType, List<Department> departments) {
        super(id, firstName, lastName, password, userType);
        this.departments = departments;
    }

    public Doctor() {
    }
}
