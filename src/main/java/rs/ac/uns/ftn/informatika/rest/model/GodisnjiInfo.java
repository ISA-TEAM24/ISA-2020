package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class GodisnjiInfo {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 15, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    @Id
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
