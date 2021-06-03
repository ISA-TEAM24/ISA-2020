package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Ponuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private int ukupnaCena;
    @OneToOne
    private Narudzbenica narudzbenica;
    @ElementCollection
    @CollectionTable(name = "ponuda_lekovi_mapping",
            joinColumns = {@JoinColumn(name = "ponuda_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "naziv_leka")
    @Column(name = "kolicina")
    private Map<String, Integer> spisakLekova;
    @OneToOne
    private Korisnik posiljalac;
    @Column(nullable = false)
    private Status status;

    public Ponuda() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(int ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Narudzbenica getNarudzbenica() {
        return narudzbenica;
    }

    public void setNarudzbenica(Narudzbenica narudzbenica) {
        this.narudzbenica = narudzbenica;
    }

    public Map<String, Integer> getSpisakLekova() {
        return spisakLekova;
    }

    public void setSpisakLekova(Map<String, Integer> spisakLekova) {
        this.spisakLekova = spisakLekova;
    }

    public Korisnik getPosiljalac() {
        return posiljalac;
    }

    public void setPosiljalac(Korisnik posiljalac) {
        this.posiljalac = posiljalac;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getPrihacena() {
        return Status.PRIHVACENA;
    }

    public Status getOdbijena() {
        return Status.ODBIJENA;
    }

    public Status getCeka() {
        return Status.CEKA;
    }

    @Override
    public String toString() {
        return "Ponuda{" +
                "ID=" + ID +
                ", ukupnaCena=" + ukupnaCena +
                ", narudzbenica=" + narudzbenica +
                ", spisakLekova=" + spisakLekova +
                ", posiljalac=" + posiljalac +
                ", status=" + status +
                '}';
    }

    enum Status {
        PRIHVACENA,
        ODBIJENA,
        CEKA
    }
}
