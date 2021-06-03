package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Promocija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private String naslov;
    @Column(nullable = false)
    private String tekst;
    @Column(nullable = false)
    private Date vaziDo;
    @OneToOne
    private Apoteka apoteka;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Date getVaziDo() {
        return vaziDo;
    }

    public void setVaziDo(Date vaziDo) {
        this.vaziDo = vaziDo;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }
}
