package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Date;

public class PrescMedDTO {

    private Date datumPrepisivanja;
    private String nazivLeka;
    private String nazivApoteke;

    public String getNazivApoteke() {
        return nazivApoteke;
    }

    public void setNazivApoteke(String nazivApoteke) {
        this.nazivApoteke = nazivApoteke;
    }

    public Date getDatumPrepisivanja() {
        return datumPrepisivanja;
    }

    public void setDatumPrepisivanja(Date datumprepisivanja) {
        this.datumPrepisivanja = datumprepisivanja;
    }

    public String getNazivLeka() {
        return nazivLeka;
    }

    public void setNazivLeka(String nazivLeka) {
        this.nazivLeka = nazivLeka;
    }
}
