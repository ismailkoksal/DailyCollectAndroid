package fr.aylan.dailycollect.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Client   implements Parcelable {

    String id;
    String logo;
    String mail;
    String tel;
    String director;
    String id_collect_point;
    String name;
    String adresse;
    String subscription_date;
    String signing_date;
    String contract_end_date;
    String collect_day;


    public Client(String id, String logo, String mail, String tel, String director, String id_collect_point, String name, String adresse,
                  String subscription_date, String signature_date, String contract_end_date, String collect_day) {
        this.id = id;
        this.logo = logo;
        this.mail = mail;
        this.tel = tel;
        this.director = director;
        this.id_collect_point = id_collect_point;
        this.name = name;
        this.adresse = adresse;
        this.subscription_date = subscription_date;
        this.signing_date = signature_date;
        this.contract_end_date = contract_end_date;
        this.collect_day = collect_day;
    }

    public String getSubscription_date() {
        return subscription_date;
    }

    public void setSubscription_date(String subscription_date) {
        this.subscription_date = subscription_date;
    }

    public String getSigning_date() {
        return signing_date;
    }

    public void setSigning_date(String signing_date) {
        this.signing_date = signing_date;
    }

    public String getContract_end_date() {
        return contract_end_date;
    }

    public void setContract_end_date(String contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    public String getCollect_day() {
        return collect_day;
    }

    public void setCollect_day(String collect_day) {
        this.collect_day = collect_day;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getId_collect_point() {
        return id_collect_point;
    }

    public void setId_collect_point(String id_collect_point) {
        this.id_collect_point = id_collect_point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(logo);
        dest.writeString(mail);
        dest.writeString(tel);
        dest.writeString(director);
        dest.writeString(id_collect_point);
        dest.writeString(name);
        dest.writeString(adresse);
        dest.writeString(subscription_date);
        dest.writeString(signing_date);
        dest.writeString(contract_end_date);
        dest.writeString(collect_day);

    }

    public Client(Parcel in) {
        id = in.readString();
        logo = in.readString();
        mail = in.readString();
        tel = in.readString();
        director = in.readString();
        id_collect_point = in.readString();
        name = in.readString();
        adresse = in.readString();
       subscription_date = in.readString();
       signing_date = in.readString();
       contract_end_date = in.readString();
       collect_day = in.readString();
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
