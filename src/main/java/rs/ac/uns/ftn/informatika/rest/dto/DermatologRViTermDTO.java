package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.List;

public class DermatologRViTermDTO {

    private String username;
    private String ime;
    private String prezime;
    private List<WorkTimeDTO> radnaVremena;
    private List<PosetaDTO> zakazaniPregledi;

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

    public List<WorkTimeDTO> getRadnaVremena() {
        return radnaVremena;
    }

    public void setRadnaVremena(List<WorkTimeDTO> radnaVremena) {
        this.radnaVremena = radnaVremena;
    }

    public List<PosetaDTO> getZakazaniPregledi() {
        return zakazaniPregledi;
    }

    public void setZakazaniPregledi(List<PosetaDTO> zakazaniPregledi) {
        this.zakazaniPregledi = zakazaniPregledi;
    }
}
