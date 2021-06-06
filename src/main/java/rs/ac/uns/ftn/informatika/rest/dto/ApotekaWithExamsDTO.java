package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Korisnik;

import java.util.List;

public class ApotekaWithExamsDTO {

    private String naziv;
    private String adresa;
    private float ocena;
    private int cenaSavetovanja;
    private List<FarmaceutDTO> farmaceuti;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public int getCenaSavetovanja() {
        return cenaSavetovanja;
    }

    public void setCenaSavetovanja(int cenaSavetovanja) {
        this.cenaSavetovanja = cenaSavetovanja;
    }

    public List<FarmaceutDTO> getFarmaceuti() {
        return farmaceuti;
    }

    public void setFarmaceuti(List<FarmaceutDTO> farmaceuti) {
        this.farmaceuti = farmaceuti;
    }
}
