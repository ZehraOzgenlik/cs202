package tr.edu.ozyegin.cs202.model;

public class Doctor extends User {
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
