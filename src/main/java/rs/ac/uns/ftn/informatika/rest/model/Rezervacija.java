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
    private Date rokZaPreuzimanje;
    @Column(nullable = true)
    private Date datumPreuz;
    @OneToOne
    private Korisnik pacijent;
    @Column(nullable = false)
    private boolean penalized;

    public Rezervacija() {
        this.penalized = false;
    }

    public Rezervacija(Long ID, Lek lek, Apoteka apoteka, Date rokZaPreuzimanje, Date datumPreuz, Korisnik pacijent, boolean penalized) {
        this.ID = ID;
        this.lek = lek;
        this.apoteka = apoteka;
        this.rokZaPreuzimanje = rokZaPreuzimanje;
        this.datumPreuz = datumPreuz;
        this.pacijent = pacijent;
        this.penalized = penalized;
    }

    public boolean isPenalized() {
        return penalized;
    }

    public void setPenalized(boolean penalized) {
        this.penalized = penalized;
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

    public Date getRokZaPreuzimanje() {
        return rokZaPreuzimanje;
    }

    public void setRokZaPreuzimanje(Date rokZaPreuzimanje) {
        this.rokZaPreuzimanje = rokZaPreuzimanje;
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "ID=" + ID +
                ", lek=" + lek +
                ", apoteka=" + apoteka +
                ", rokZaPreuzimanje=" + rokZaPreuzimanje +
                ", datumPreuz=" + datumPreuz +
                ", pacijent=" + pacijent +
                '}';
    }
}

