package tr.edu.ozyegin.cs202.service.model;

import java.util.List;

public class Doctor extends User {
    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
