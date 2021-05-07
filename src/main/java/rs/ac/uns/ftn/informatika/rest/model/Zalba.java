package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Zalba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne
    private Korisnik od;
    @OneToOne
    private Korisnik za;
    @Column(nullable = false)
    private Date datum;
    @Column(nullable = false)
    private String text;


}
