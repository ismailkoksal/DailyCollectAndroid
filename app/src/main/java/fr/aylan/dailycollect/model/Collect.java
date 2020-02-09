package fr.aylan.dailycollect.model;

public class Collect {
    // date of the collect
    private String date;
    // time of the collect
    private String time;

    public Collect() {}

    public Collect(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
