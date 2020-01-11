package fr.aylan.dailycollect.driver.model;

public class Client {

    String id;
    String logo;
    String mail;
    String tel;
    String director;
    String id_collect_point;
    String name;


    public Client(String id, String logo, String mail, String tel, String director, String id_collect_point, String name) {
        this.id = id;
        this.logo = logo;
        this.mail = mail;
        this.tel = tel;
        this.director = director;
        this.id_collect_point = id_collect_point;
        this.name = name;
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
}
