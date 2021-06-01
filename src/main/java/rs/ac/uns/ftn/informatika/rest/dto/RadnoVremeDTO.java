package rs.ac.uns.ftn.informatika.rest.dto;

import java.time.LocalTime;
import java.util.Date;

public class RadnoVremeDTO {

    private String odDatum;
    private String doDatum;
    private LocalTime odVreme;
    private LocalTime doVreme;
    private String apoteka;

    public String getOdDatum() {
        return odDatum;
    }

    public void setOdDatum(String odDatum) {
        this.odDatum = odDatum;
    }

    public String getDoDatum() {
        return doDatum;
    }

    public void setDoDatum(String doDatum) {
        this.doDatum = doDatum;
    }

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
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
}
