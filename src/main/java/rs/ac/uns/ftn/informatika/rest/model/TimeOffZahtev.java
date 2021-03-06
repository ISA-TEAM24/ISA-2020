package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class TimeOffZahtev {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @Column(nullable = false)
    private Date odDatuma;
    @Column(nullable = false)
    private Date doDatuma;
    @OneToOne
    private Korisnik podnosilac;
    @Column(nullable = false)
    private Stanje stanjeZahteva;
    @Column(nullable = false)
    private String razlog;
    @Column(nullable = false)
    private Vrsta vrsta;

    public TimeOffZahtev() {
    }

    public TimeOffZahtev(Long ID, Date odDatuma, Date doDatuma, Korisnik podnosilac, Stanje stanjeZahteva, String razlog, Vrsta vrsta) {
        this.ID = ID;
        this.odDatuma = odDatuma;
        this.doDatuma = doDatuma;
        this.podnosilac = podnosilac;
        this.stanjeZahteva = stanjeZahteva;
        this.razlog = razlog;
        this.vrsta = vrsta;
    }

    public enum Vrsta {
        GODISNJI,
        ODSUSTVO
    }

    public enum Stanje {
        AKTIVAN,
        PRIHVACEN,
        ODBIJEN
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getOdDatuma() {
        return odDatuma;
    }

    public void setOdDatuma(Date odDatuma) {
        this.odDatuma = odDatuma;
    }

    public Date getDoDatuma() {
        return doDatuma;
    }

    public void setDoDatuma(Date doDatuma) {
        this.doDatuma = doDatuma;
    }

    public Korisnik getPodnosilac() {
        return podnosilac;
    }

    public void setPodnosilac(Korisnik podnosilac) {
        this.podnosilac = podnosilac;
    }

    public Stanje getStanjeZahteva() {
        return stanjeZahteva;
    }

    public void setStanjeZahteva(Stanje stanjeZahteva) {
        this.stanjeZahteva = stanjeZahteva;
    }

    public String getRazlog() {
        return razlog;
    }

    public void setRazlog(String razlog) {
        this.razlog = razlog;
    }

    public Vrsta getVrsta() {
        return vrsta;
    }

    public void setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
    }
}
