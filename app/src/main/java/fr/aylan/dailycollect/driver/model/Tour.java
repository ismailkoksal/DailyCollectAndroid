package fr.aylan.dailycollect.driver.model;

import java.util.ArrayList;

public class Tour {

    private int id;
    // dd/mm/yyyy
    private String date;
    private String id_Rider;
    private ArrayList<String> list_collectPoints;
    private String city;


    public Tour(int id, String date, String id_Rider, String city) {
        this.id = id;
        this.date = date;
        this.id_Rider = id_Rider;
        this.city = city;
        list_collectPoints = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_Rider() {
        return id_Rider;
    }

    public void setId_Rider(String id_Rider) {
        this.id_Rider = id_Rider;
    }

    public ArrayList<String> getList_collectPoints() {
        return list_collectPoints;
    }

    public void setList_collectPoints(ArrayList<String> list_collectPoints) {
        this.list_collectPoints = list_collectPoints;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void addCollectPoint(String collectPoint){
        this.addCollectPoint(collectPoint);
    }

    public String getItem(int index){
        return  list_collectPoints.get(index);
    }
}
