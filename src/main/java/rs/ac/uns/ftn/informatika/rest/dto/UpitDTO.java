package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Lek;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class UpitDTO {

    private String id;
    private Lek lek;
    private int kolicina;
    private Apoteka apoteka;
    private boolean uspesan;
    private Korisnik posiljalac;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Lek getLek() {
        return lek;
    }

    public void setLek(Lek lek) {
        this.lek = lek;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public boolean isUspesan() {
        return uspesan;
    }

    public void setUspesan(boolean uspesan) {
        this.uspesan = uspesan;
    }

    public Korisnik getPosiljalac() {
        return posiljalac;
    }

    public void setPosiljalac(Korisnik posiljalac) {
        this.posiljalac = posiljalac;
    }
}
