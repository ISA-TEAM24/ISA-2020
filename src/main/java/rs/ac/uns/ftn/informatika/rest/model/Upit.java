package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Upit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Lek lek;
    @Column(nullable = false)
    private int kolicina;
    @OneToOne
    private Apoteka apoteka;
    @Column(nullable = false)
    private boolean uspesan;
    @OneToOne
    private Korisnik posiljalac;
    @Column(nullable = false)
    private Date datum;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
