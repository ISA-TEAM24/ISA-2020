package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Date;
import java.util.List;

public class NarudzbenicaDTO {

    private Date rok;
    private List<NarudzbenicaLekDTO> lekovi;

    public Date getRok() {
        return rok;
    }

    public void setRok(Date rok) {
        this.rok = rok;
    }

    public List<NarudzbenicaLekDTO> getLekovi() {
        return lekovi;
    }

    public void setLekovi(List<NarudzbenicaLekDTO> lekovi) {
        this.lekovi = lekovi;
    }
}
