package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Apoteka;

import java.util.List;

public class DermDTO {

    private String ime;
    private String prezime;
    private float ocena;
    private String username;
    private List<Apoteka> radiUApotekama;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Apoteka> getRadiUApotekama() {
        return radiUApotekama;
    }

    public void setRadiUApotekama(List<Apoteka> radiUApotekama) {
        this.radiUApotekama = radiUApotekama;
    }
}
