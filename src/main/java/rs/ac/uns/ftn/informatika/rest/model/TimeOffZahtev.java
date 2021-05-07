package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class TimeOffZahtev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private Date odDatuma;
    @Column(nullable = false)
    private Date doDatuma;
    @OneToOne
    private Korisnik podnosilac;
    @Column(nullable = false)
    private boolean prihvacen;
    @Column(nullable = false)
    private String razlog;
    @Column(nullable = false)
    private Vrsta vrsta;



    enum Vrsta {
        GODISNJI,
        ODSUSTVO
    }
}
