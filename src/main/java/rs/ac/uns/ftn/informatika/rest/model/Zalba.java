package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Zalba {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @OneToOne
    private Korisnik od;
    @Column(nullable = false)
    private String vrstaPrimaoca;
    @Column(nullable = false)
    private Long idPrimaoca;
    @Column(nullable = false)
    private Date datum;
    @Column(nullable = false)
    private String text;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Korisnik getOd() {
        return od;
    }

    public void setOd(Korisnik od) {
        this.od = od;
    }

    public String getVrstaPrimaoca() {
        return vrstaPrimaoca;
    }

    public void setVrstaPrimaoca(String vrstaPrimaoca) {
        this.vrstaPrimaoca = vrstaPrimaoca;
    }

    public Long getIdPrimaoca() {
        return idPrimaoca;
    }

    public void setIdPrimaoca(Long idPrimaoca) {
        this.idPrimaoca = idPrimaoca;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
