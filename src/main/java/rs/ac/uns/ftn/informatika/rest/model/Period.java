package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
@Entity
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private LocalTime odVreme;
    @Column(nullable = false)
    private LocalTime doVreme;
    @Column(nullable = false)
    private Date odDatum;
    @Column(nullable = false)
    private Date doDatum;


}
