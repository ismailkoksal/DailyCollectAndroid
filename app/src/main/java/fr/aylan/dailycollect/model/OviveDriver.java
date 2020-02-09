package fr.aylan.dailycollect.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OviveDriver implements Parcelable{

    private String id;
    // dd/mm/yyyy
    private String employement_date;
    // name
    private String name;
    // E-mail
    private String mail;
    // phone number
    private String tel;
    // City
    private String city;
    // URL of the profile photo
    private String urlPhoto;

    public OviveDriver(String id, String employement_date, String name, String mail, String tel, String city, String urlPhoto) {
        this.id = id;
        this.employement_date = employement_date;
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.city = city;
        this.urlPhoto = urlPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployement_date() {
        return employement_date;
    }

    public void setEmployement_date(String employement_date) {
        this.employement_date = employement_date;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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
        dest.writeString(id);
        dest.writeString(employement_date);
        dest.writeString(name);
        dest.writeString(tel);
        dest.writeString(mail);
        dest.writeString(city);
        dest.writeString(urlPhoto);
    }

    public OviveDriver(Parcel in) {
        id = in.readString();
        employement_date = in.readString();
        name = in.readString();
        tel = in.readString();
        mail = in.readString();
        city = in.readString();
        urlPhoto = in.readString();
    }

    public static final Parcelable.Creator<OviveDriver> CREATOR = new Parcelable.Creator<OviveDriver>() {
        public OviveDriver createFromParcel(Parcel in) {
            return new OviveDriver(in);
        }

        public OviveDriver[] newArray(int size) {
            return new OviveDriver[size];
        }
    };
}
