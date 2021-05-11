package rs.ac.uns.ftn.informatika.rest.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class Korisnik implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = false)
    private String password;
    @Column(nullable = false, unique = false)
    private Date lastPasswordResetDate;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    //Pacijent
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Terapija> terapije;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LoyaltyInfo loyaltyInfo;

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

    public Korisnik() {
    }

    public Korisnik(Long ID, String email, String username, String password, Date lastPasswordResetDate, String ime,
                    String prezime, String adresa, String grad, String drzava, String telefon, boolean activated,
                    Set<Authority> authorities // , Set<Terapija> terapije, LoyaltyInfo loyaltyInfo, Set<Lek> alergije,
                    //Map<String, RadnoInfo> radnoInfo, GodisnjiInfo godisnjiInfo, float ocena, boolean prvoLogovanje
    ) {
        this.ID = ID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.telefon = telefon;
        this.activated = activated;
        this.authorities = authorities;
        this.terapije = new HashSet<>();
        this.loyaltyInfo = new LoyaltyInfo();
        this.alergije = new HashSet<Lek>();
        this.radnoInfo = new HashMap<String, RadnoInfo>();
        this.godisnjiInfo = new GodisnjiInfo();
        this.ocena = 5;
        this.prvoLogovanje = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.activated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPasswordResetDate() {
        return this.lastPasswordResetDate;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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

    public void setRadnoInfo(Map<String, RadnoInfo> radnoInfo) {
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

    /* Uloga {
        ADMIN_APOTEKA,
        ADMIN_SISTEM,
        DOBAVLJAC,
        FARMACEUT,
        DERMATOLOG,
        PACIJENT
    } */
}
