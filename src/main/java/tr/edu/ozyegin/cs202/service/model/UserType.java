package tr.edu.ozyegin.cs202.service.model;

public enum UserType {
    PATIENT(1, "Patient"),
    DOCTOR(2, "Doctor"),
    NURSE(3, "Nurse"),
    MANAGER(4, "Manager");

    private int id;
    private String name;

    UserType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserType getById(int id) throws Exception {
        for (UserType userType : UserType.values()) {
            if (userType.id == id)
                return userType;
        }
        throw new Exception("Unknown user type");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
