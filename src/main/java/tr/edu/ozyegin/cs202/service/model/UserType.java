package tr.edu.ozyegin.cs202.service.model;

public class UserType {
    private int id;
    private String name;

    public UserType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
