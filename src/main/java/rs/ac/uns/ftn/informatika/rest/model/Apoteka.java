package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Apoteka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false, unique = true)
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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<Korisnik> getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(List<Korisnik> zaposleni) {
        this.zaposleni = zaposleni;
    }

    public Map<String, Integer> getCenovnik() {
        return cenovnik;
    }

    public void setCenovnik(Map<String, Integer> cenovnik) {
        this.cenovnik = cenovnik;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public Map<Long, Integer> getMagacin() {
        return magacin;
    }

    public void setMagacin(Map<Long, Integer> magacin) {
        this.magacin = magacin;
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
