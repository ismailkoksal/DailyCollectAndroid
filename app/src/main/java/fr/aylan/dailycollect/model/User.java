package fr.aylan.dailycollect.model;

public class User {
    // id
    private String id;
    // Driver, Client, Ovive's staff
    private String type;

    public User() {}

    public User(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String  getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
