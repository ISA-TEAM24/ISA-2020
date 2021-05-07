package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class LoyaltyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ID;
    @Column(nullable = false)
    private Klasa klasa;
    @Column(nullable = false)
    private int poeni;
    @Column(nullable = false)
    private int penali;
    @ElementCollection
    @CollectionTable(name = "loyalty_apoteka_mapping",
            joinColumns = {@JoinColumn(name = "loyalty_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "naziv_apoteke")
    @Column(name = "prati")
    private Map<String, Boolean> pratiPromocije = new HashMap<String, Boolean>();



    enum Klasa {
        REGULAR,
        SILVER,
        GOLD
    }
}
