package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Lek lek;
    @OneToOne
    private Apoteka apoteka;
    @Column(nullable = false)
    private Date datumPreuz;
    @OneToOne
    private Korisnik pacijent;

    public Rezervacija() {
    }

    public Rezervacija(Long ID, Lek lek, Apoteka apoteka, Date datumPreuz, Korisnik pacijent) {
        this.ID = ID;
        this.lek = lek;
        this.apoteka = apoteka;
        this.datumPreuz = datumPreuz;
        this.pacijent = pacijent;
    }

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

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public Date getDatumPreuz() {
        return datumPreuz;
    }

    public void setDatumPreuz(Date datumPreuz) {
        this.datumPreuz = datumPreuz;
    }

    public Korisnik getPacijent() {
        return pacijent;
    }

    public void setPacijent(Korisnik pacijent) {
        this.pacijent = pacijent;
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "ID=" + ID +
                ", lek=" + lek +
                ", apoteka=" + apoteka +
                ", datumPreuz=" + datumPreuz +
                ", pacijent=" + pacijent +
                '}';
    }
}

