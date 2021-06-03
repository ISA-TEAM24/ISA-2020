package rs.ac.uns.ftn.informatika.rest.dto;

public class LekDTO {

    private String naziv;
    private boolean uMojojApoteci;
    private String proizvodjac;
    private String sastav;
    private float ocena;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isuMojojApoteci() {
        return uMojojApoteci;
    }

    public void setuMojojApoteci(boolean uMojojApoteci) {
        this.uMojojApoteci = uMojojApoteci;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getSastav() {
        return sastav;
    }

    public void setSastav(String sastav) {
        this.sastav = sastav;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }
}
