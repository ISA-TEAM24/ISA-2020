package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Odgovor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Korisnik od;
    @OneToOne
    private Korisnik za;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Date datum;

}
