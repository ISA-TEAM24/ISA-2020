package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Apoteka {

    @Id
    private String ID;
    @Column(nullable = false, unique = false)
    private String naziv;
    @Column(nullable = false, unique = false)
    private String adresa;
    @Column(nullable = false, unique = false)
    private String opis;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Korisnik> zaposleni;
    @ElementCollection
    @CollectionTable(name = "apoteka_cenovnik_mapping",
            joinColumns = {@JoinColumn(name = "apoteka_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "naziv_predmeta")
    @Column(name = "cena")
    private Map<String, Integer> cenovnik;
    private float ocena;
    @ElementCollection
    @CollectionTable(name = "apoteka_magacin_mapping",
            joinColumns = {@JoinColumn(name = "apoteka_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "id_leka")
    @Column(name = "kolicina")
    private Map<Long, Integer> magacin;

    public Apoteka() {

    }



    @Override
    public String toString() {
        return "Apoteka{" +
                "ID='" + ID + '\'' +
                ", naziv='" + naziv + '\'' +
                ", adresa='" + adresa + '\'' +
                ", opis='" + opis + '\'' +
                ", zaposleni=" + zaposleni +
                ", cenovnik=" + cenovnik +
                ", ocena=" + ocena +
                ", magacin=" + magacin +
                '}';
    }
}
