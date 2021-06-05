package rs.ac.uns.ftn.informatika.rest.dto;

public class PriceListDTO {

    private String naziv;
    private int cena;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}
