package tr.edu.ozyegin.cs202.model;

public enum TreatmentType {
    OUTPATIENT(1, "Outpatient"),
    INPATIENT(2, "Inpatient");

    private int id;
    private String name;

    TreatmentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TreatmentType getById(int id) throws Exception {
        for (TreatmentType userType : TreatmentType.values()) {
            if (userType.id == id)
                return userType;
        }
        throw new Exception("Unknown treatment type");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
