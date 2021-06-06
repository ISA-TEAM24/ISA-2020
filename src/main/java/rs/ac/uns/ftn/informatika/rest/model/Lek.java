package rs.ac.uns.ftn.informatika.rest.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Lek {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @Column(nullable = false, unique = false)
    private String naziv;
    @Column(nullable = false, unique = false)
    private String vrsta;
    @Column(nullable = false, unique = false)
    private String oblik;
    @Column(nullable = false, unique = false)
    private String sastav;
    @Column(nullable = false, unique = false)
    private String proizvodjac;
    @Column(nullable = false, unique = false)
    private boolean naRecept;

    @ElementCollection
    private Set<Long> alternative = new HashSet<Long>();
    @Column(nullable = false, unique = false)
    private String napomene;
    @Column(nullable = false, unique = false)
    private int poeni;
    @Column(nullable = false, unique = false)
    private float ocena;

    public Lek(){
    }

    public Lek(Long ID, String naziv, String vrsta, String oblik, String sastav, String proizvodjac, boolean naRecept,
               Set<Long> alternative, String napomene, int poeni, float ocena) {
        this.ID = ID;
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.oblik = oblik;
        this.sastav = sastav;
        this.proizvodjac = proizvodjac;
        this.naRecept = naRecept;
        this.alternative = alternative;
        this.napomene = napomene;
        this.poeni = poeni;
        this.ocena = ocena;
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

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getOblik() {
        return oblik;
    }

    public void setOblik(String oblik) {
        this.oblik = oblik;
    }

    public String getSastav() {
        return sastav;
    }

    public void setSastav(String sastav) {
        this.sastav = sastav;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public boolean isNaRecept() {
        return naRecept;
    }

    public void setNaRecept(boolean naRecept) {
        this.naRecept = naRecept;
    }

    public Set<Long> getAlternative() {
        return alternative;
    }

    public void setAlternative(Set<Long> alternative) {
        this.alternative = alternative;
    }

    public String getNapomene() {
        return napomene;
    }

    public void setNapomene(String napomene) {
        this.napomene = napomene;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Lek{" +
                "ID=" + ID +
                ", naziv='" + naziv + '\'' +
                ", vrsta='" + vrsta + '\'' +
                ", oblik='" + oblik + '\'' +
                ", sastav='" + sastav + '\'' +
                ", proizvodjac='" + proizvodjac + '\'' +
                ", naRecept=" + naRecept +
                ", alternative=" + alternative +
                ", napomene='" + napomene + '\'' +
                ", poeni=" + poeni +
                ", ocena=" + ocena +
                '}';
    }
}
