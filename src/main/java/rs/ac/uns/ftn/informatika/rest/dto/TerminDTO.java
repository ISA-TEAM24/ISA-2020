package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Korisnik;

public class TerminDTO {

    private Long ID;
    private Korisnik zaposleni;
    private String datum;
    private String vreme;
    private int cena;

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Korisnik getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Korisnik zaposleni) {
        this.zaposleni = zaposleni;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }
}
