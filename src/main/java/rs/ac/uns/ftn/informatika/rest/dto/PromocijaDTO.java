package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Date;

public class PromocijaDTO {

    private String naslov;
    private Date datum;
    private String tekst;

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
}
