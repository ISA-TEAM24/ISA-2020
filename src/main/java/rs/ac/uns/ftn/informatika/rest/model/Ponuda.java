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
