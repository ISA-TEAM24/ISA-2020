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

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalTime getOdVreme() {
        return odVreme;
    }

    public void setOdVreme(LocalTime odVreme) {
        this.odVreme = odVreme;
    }

    public LocalTime getDoVreme() {
        return doVreme;
    }

    public void setDoVreme(LocalTime doVreme) {
        this.doVreme = doVreme;
    }

    public Date getOdDatum() {
        return odDatum;
    }

    public void setOdDatum(Date odDatum) {
        this.odDatum = odDatum;
    }

    public Date getDoDatum() {
        return doDatum;
    }

    public void setDoDatum(Date doDatum) {
        this.doDatum = doDatum;
    }
}
