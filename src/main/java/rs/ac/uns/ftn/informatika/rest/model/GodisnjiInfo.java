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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public boolean isNaGodisnjem() {
        return naGodisnjem;
    }

    public void setNaGodisnjem(boolean naGodisnjem) {
        this.naGodisnjem = naGodisnjem;
    }

    public Date getOdDatuma() {
        return odDatuma;
    }

    public void setOdDatuma(Date odDatuma) {
        this.odDatuma = odDatuma;
    }

    public Date getDoDatuma() {
        return doDatuma;
    }

    public void setDoDatuma(Date doDatuma) {
        this.doDatuma = doDatuma;
    }
}
