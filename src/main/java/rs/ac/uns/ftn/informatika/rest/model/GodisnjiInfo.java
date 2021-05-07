package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class GodisnjiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private boolean naGodisnjem;
    @Column(nullable = false)
    private Date odDatuma;
    @Column(nullable = false)
    private Date doDatuma;


}
