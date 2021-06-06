package rs.ac.uns.ftn.informatika.rest.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class HireDermatologistDTO {
    private String username;
    private Date odDatum;
    private Date doDatum;
    private LocalTime odVreme;
    private LocalTime doVreme;
    private List<Integer> neradniDani;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Integer> getNeradniDani() {
        return neradniDani;
    }

    public void setNeradniDani(List<Integer> neradniDani) {
        this.neradniDani = neradniDani;
    }
}
