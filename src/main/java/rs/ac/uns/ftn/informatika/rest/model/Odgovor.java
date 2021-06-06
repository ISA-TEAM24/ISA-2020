package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Odgovor {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
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
