package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = false)
    private String pw;
    @Column(nullable = false, unique = false)
    private String ime;
    @Column(nullable = false, unique = false)
    private String prezime;
    @Column(nullable = false, unique = false)
    private String adresa;
    @Column(nullable = false, unique = false)
    private String grad;
    @Column(nullable = false, unique = false)
    private String drzava;
    @Column(nullable = false, unique = true)
    private String telefon;
    @Column(nullable = false, unique = false)
    private boolean activated;
    @Column(nullable = false, unique = false)
    private Uloga uloga;

    //Pacijent
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Terapija> terapije;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LoyaltyInfo loyaltyInfo;

    public Korisnik() {
    }
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Lek> alergije;

    //Zaposleni
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<String, RadnoInfo> radnoInfo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GodisnjiInfo godisnjiInfo;
    @Column(nullable = false, unique = false)
    private float ocena;
    @Column(nullable = false, unique = false)
    private boolean prvoLogovanje;

    /*
    public Korisnik(){

    }

    //Pacijent konstruktor
    public Korisnik(Long ID, String email, String pw, String ime, String prezime, String adresa, String grad,
                    String drzava, String telefon, boolean activated, Uloga uloga, Set<Terapija> terapije,
                    LoyaltyInfo loyaltyInfo, Set<Lek> alergije) {
        this.ID = ID;
        this.email = email;
        this.pw = pw;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.telefon = telefon;
        this.activated = activated;
        this.uloga = uloga;
        this.terapije = terapije;
        this.loyaltyInfo = loyaltyInfo;
        this.alergije = alergije;
    }

    //Zaposleni konstruktor


    public Korisnik(Long ID, String email, String pw, String ime, String prezime, String adresa, String grad,
                    String drzava, String telefon, boolean activated, Uloga uloga, HashMap<String, RadnoInfo> radnoInfo,
                    GodisnjiInfo godisnjiInfo, float ocena, boolean prvoLogovanje) {
        this.ID = ID;
        this.email = email;
        this.pw = pw;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.telefon = telefon;
        this.activated = activated;
        this.uloga = uloga;
        this.radnoInfo = radnoInfo;
        this.godisnjiInfo = godisnjiInfo;
        this.ocena = ocena;
        this.prvoLogovanje = prvoLogovanje;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public Set<Terapija> getTerapije() {
        return terapije;
    }

    public void setTerapije(Set<Terapija> terapije) {
        this.terapije = terapije;
    }

    public LoyaltyInfo getLoyaltyInfo() {
        return loyaltyInfo;
    }

    public void setLoyaltyInfo(LoyaltyInfo loyaltyInfo) {
        this.loyaltyInfo = loyaltyInfo;
    }

    public Set<Lek> getAlergije() {
        return alergije;
    }

    public void setAlergije(Set<Lek> alergije) {
        this.alergije = alergije;
    }

    public Map<String, RadnoInfo> getRadnoInfo() {
        return radnoInfo;
    }

    public void setRadnoInfo(HashMap<String, RadnoInfo> radnoInfo) {
        this.radnoInfo = radnoInfo;
    }

    public GodisnjiInfo getGodisnjiInfo() {
        return godisnjiInfo;
    }

    public void setGodisnjiInfo(GodisnjiInfo godisnjiInfo) {
        this.godisnjiInfo = godisnjiInfo;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public boolean isPrvoLogovanje() {
        return prvoLogovanje;
    }

    public void setPrvoLogovanje(boolean prvoLogovanje) {
        this.prvoLogovanje = prvoLogovanje;
    }

    */

    enum Uloga {
        ADMIN_APOTEKA,
        ADMIN_SISTEM,
        DOBAVLJAC,
        FARMACEUT,
        DERMATOLOG,
        PACIJENT
    }
}
