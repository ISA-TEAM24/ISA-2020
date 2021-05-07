package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class Upit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Lek lek;
    @Column(nullable = false)
    private int kolicina;
    @OneToOne
    private Apoteka apoteka;
    @Column(nullable = false)
    private boolean uspesan;
    @OneToOne
    private Korisnik posiljalac;


}
