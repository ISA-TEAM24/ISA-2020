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
    @Column(nullable = true)
    private Date odDatuma;
    @Column(nullable = true)
    private Date doDatuma;

    public GodisnjiInfo(){
        naGodisnjem = false;

    }
}
