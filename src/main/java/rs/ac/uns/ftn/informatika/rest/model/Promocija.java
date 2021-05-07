package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Promocija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private String predmet;
    @Column(nullable = false)
    private int popust;
    @Column(nullable = false)
    private Date vaziDo;
    @Column(nullable = false)
    private String kod;
    @Column(nullable = false)
    private String text;
    @OneToOne
    private Apoteka apoteka;


}
