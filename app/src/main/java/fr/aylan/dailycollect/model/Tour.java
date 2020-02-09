package fr.aylan.dailycollect.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Tour  implements Parcelable {

    private int id;
    // dd/mm/yyyy
    private String date;
    // the driver id
    private String id_Rider;
    // list of collect points
    private ArrayList<String> list_collectPoints;
    // City
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

    public void addCollectPoint(String _collectPoint){
        this.list_collectPoints.add(_collectPoint);
    }

    public String getItem(int index){
        return  list_collectPoints.get(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(id_Rider);
        dest.writeList(list_collectPoints);
        dest.writeString(city);
    }

    public Tour(Parcel in) {
        id = in.readInt();
        date = in.readString();
        id_Rider = in.readString();
        list_collectPoints = in.readArrayList(String.class.getClassLoader());
        city = in.readString();
    }

    public static final Parcelable.Creator<Tour> CREATOR = new Parcelable.Creator<Tour>() {
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };
}
