package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.List;

public class DermatologRadnoVremeDTO {

    private String username;
    private String ime;
    private String prezime;
    private List<RadnoVremeDTO> radnaVremena;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public List<RadnoVremeDTO> getRadnaVremena() {
        return radnaVremena;
    }

    public void setRadnaVremena(List<RadnoVremeDTO> radnaVremena) {
        this.radnaVremena = radnaVremena;
    }
}
