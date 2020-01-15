package fr.aylan.dailycollect.model;

public class User {
    private String id;
    private UserType type;

    public User() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
