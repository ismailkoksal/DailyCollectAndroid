package fr.aylan.dailycollect.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    // list of the collect Points
    private ArrayList<Collect> collects;
    // name
    private String nom;
    private String raisonSociale;
    // E-Mail
    private String email;
    // phone number
    private String telephone;
    // street
    private String rue;
    // ZipCode
    private String cp;
    // City
    private String ville;

    public Customer() {}

    public Customer(ArrayList<Collect> collects, String nom, String raisonSociale, String email, String telephone, String rue, String cp, String ville) {
        this.collects = collects;
        this.nom = nom;
        this.raisonSociale = raisonSociale;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
    }

    public Customer(ArrayList<Collect> collects, String nom, String email, String telephone, String rue, String cp, String ville) {
        this.collects = collects;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
    }

    public List<Collect> getCollects() {
        return collects;
    }

    public void setCollects(ArrayList<Collect> collects) {
        this.collects = collects;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
