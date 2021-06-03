package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Narudzbenica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @ElementCollection
    @CollectionTable(name = "narudzbenica_lekovi_mapping",
            joinColumns = {@JoinColumn(name = "narudzbenica_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "naziv_leka")
    @Column(name = "kolicina")
    private Map<String,Integer> spisakLekova;
    @Column(nullable = false)
    private Date rokZaPonudu;
    @OneToOne
    private Apoteka apoteka;
    @Column(nullable = false)
    private Status status;
    @OneToOne
    private Korisnik kreirao;

    public Narudzbenica() {
    }

    public Narudzbenica(Long ID, Map<String, Integer> spisakLekova, Date rokZaPonudu, Apoteka apoteka, Status status, Korisnik kreirao) {
        this.ID = ID;
        this.spisakLekova = spisakLekova;
        this.rokZaPonudu = rokZaPonudu;
        this.apoteka = apoteka;
        this.status = status;
        this.kreirao = kreirao;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Map<String, Integer> getSpisakLekova() {
        return spisakLekova;
    }

    public void setSpisakLekova(Map<String, Integer> spisakLekova) {
        this.spisakLekova = spisakLekova;
    }

    public Date getRokZaPonudu() {
        return rokZaPonudu;
    }

    public void setRokZaPonudu(Date rokZaPonudu) {
        this.rokZaPonudu = rokZaPonudu;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Korisnik getKreirao() {
        return kreirao;
    }

    public void setKreirao(Korisnik kreirao) {
        this.kreirao = kreirao;
    }

    public enum Status {
        CEKA,
        OBRADJENA
    }

    public Status getCeka() { return Status.CEKA;}

    public Status getObradjena() { return Status.OBRADJENA;}
}
