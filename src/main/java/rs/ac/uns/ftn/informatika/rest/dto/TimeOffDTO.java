package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Date;

public class TimeOffDTO {

    private Date odDatuma;
    private Date doDatuma;
    private String vrsta;
    private String razlog;
    private String stanje;

    public TimeOffDTO() {
    }

    public TimeOffDTO(Date odDatuma, Date doDatuma, String vrsta, String razlog, String stanje) {
        this.odDatuma = odDatuma;
        this.doDatuma = doDatuma;
        this.vrsta = vrsta;
        this.razlog = razlog;
        this.stanje = stanje;
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

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getRazlog() {
        return razlog;
    }

    public void setRazlog(String razlog) {
        this.razlog = razlog;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }
}
