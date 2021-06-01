package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private Long recipientID;
    @OneToOne
    private Korisnik od;
    @Column(nullable = false)
    private VrstaPrimaoca vrstaPrimaoca;
    @Column(nullable = false)
    private int ocena;
    @Column(nullable = false)
    private String note;

    private enum VrstaPrimaoca {
        OSOBA,
        LEK,
        APOTEKA
    }
}
