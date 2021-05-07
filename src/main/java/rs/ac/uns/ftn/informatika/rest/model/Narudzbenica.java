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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Apoteka apoteka;
    @Column(nullable = false)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Korisnik kreirao;

    public Narudzbenica() {
    }


    enum Status {
        CEKA,
        OBRADJENA
    }
}
