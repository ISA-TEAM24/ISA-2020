package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;


import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

public class PosetaDTO {

    private String vreme;
    private String datum;
    private int trajanje;
    private String zaposleni;
    private String apoteka;
    private int poeni;

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public String getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(String zaposleni) {
        this.zaposleni = zaposleni;
    }

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }
}
