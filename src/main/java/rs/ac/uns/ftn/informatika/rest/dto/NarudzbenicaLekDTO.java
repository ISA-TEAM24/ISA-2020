package rs.ac.uns.ftn.informatika.rest.dto;

public class NarudzbenicaLekDTO {

    private String naziv;
    private int kolicina;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
