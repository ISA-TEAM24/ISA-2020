package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Date;
import java.util.List;

public class OrderWithDetailsDTO {

    private String username;
    private String rok;
    private List<NarudzbenicaLekDTO> lekovi;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public List<NarudzbenicaLekDTO> getLekovi() {
        return lekovi;
    }

    public void setLekovi(List<NarudzbenicaLekDTO> lekovi) {
        this.lekovi = lekovi;
    }
}
