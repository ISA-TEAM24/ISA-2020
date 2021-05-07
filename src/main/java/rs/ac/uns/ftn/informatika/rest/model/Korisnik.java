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


    enum Uloga {
        ADMIN_APOTEKA,
        ADMIN_SISTEM,
        DOBAVLJAC,
        FARMACEUT,
        DERMATOLOG,
        PACIJENT
    }
}
