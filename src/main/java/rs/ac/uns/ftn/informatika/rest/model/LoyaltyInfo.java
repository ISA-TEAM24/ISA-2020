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
    @Column(nullable = true)
    private Klasa klasa;
    @Column(nullable = true)
    private int poeni;
    @Column(nullable = true)
    private int penali;
    @ElementCollection
    @CollectionTable(name = "loyalty_apoteka_mapping",
            joinColumns = {@JoinColumn(name = "loyalty_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "naziv_apoteke")
    @Column(name = "prati")
    private Map<String, Boolean> pratiPromocije;

    public LoyaltyInfo() {
        klasa = Klasa.REGULAR;
        poeni = 0;
        penali = 0;
        pratiPromocije = new HashMap<>();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Klasa getKlasa() {
        return klasa;
    }

    public void setKlasa(Klasa klasa) {
        this.klasa = klasa;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }

    public int getPenali() {
        return penali;
    }

    public void setPenali(int penali) {
        this.penali = penali;
    }

    public Map<String, Boolean> getPratiPromocije() {
        return pratiPromocije;
    }

    public void setPratiPromocije(Map<String, Boolean> pratiPromocije) {
        this.pratiPromocije = pratiPromocije;
    }

    enum Klasa {
        REGULAR,
        SILVER,
        GOLD
    }
}
