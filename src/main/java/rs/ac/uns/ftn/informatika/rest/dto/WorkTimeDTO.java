package rs.ac.uns.ftn.informatika.rest.dto;

import java.time.LocalTime;
import java.util.List;

public class WorkTimeDTO {
    private String odDatum;
    private String doDatum;
    private LocalTime odVreme;
    private LocalTime doVreme;
    private String apoteka;
    private List<Integer> neradnidani;

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

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public List<Integer> getNeradnidani() {
        return neradnidani;
    }

    public void setNeradnidani(List<Integer> neradnidani) {
        this.neradnidani = neradnidani;
    }
}
