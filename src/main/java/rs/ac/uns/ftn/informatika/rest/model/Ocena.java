package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Korisnik za;
    @OneToOne
    private Korisnik od;
    @Column(nullable = false)
    private int ocena;
    @Column(nullable = false)
    private String note;


}
