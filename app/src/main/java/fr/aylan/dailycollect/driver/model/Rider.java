package fr.aylan.dailycollect.driver.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rider implements Parcelable{

    private int id;
    // dd/mm/yyyy
    private String employement_date;
    private String name;
    private String mail;
    private String tel;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployement_date() {
        return employement_date;
    }

    public void setEmployement_date(String employement_date) {
        this.employement_date = employement_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(employement_date);
        dest.writeString(name);
        dest.writeString(tel);
        dest.writeString(mail);
        dest.writeString(city);
    }

    public Rider(Parcel in) {
        id = in.readInt();
        employement_date = in.readString();
        name = in.readString();
        tel = in.readString();
        mail = in.readString();
        city = in.readString();
    }

    public static final Parcelable.Creator<Rider> CREATOR = new Parcelable.Creator<Rider>() {
        public Rider createFromParcel(Parcel in) {
            return new Rider(in);
        }

        public Rider[] newArray(int size) {
            return new Rider[size];
        }
    };
}
