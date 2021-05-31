package rs.ac.uns.ftn.informatika.rest.dto;

public class ApotekaEditDTO {

    private String starinaziv;
    private String naziv;
    private String adresa;
    private String opis;
    private boolean menjannaziv;

    public String getStarinaziv() {
        return starinaziv;
    }

    public void setStarinaziv(String starinaziv) {
        this.starinaziv = starinaziv;
    }

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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isMenjannaziv() {
        return menjannaziv;
    }

    public void setMenjannaziv(boolean menjannaziv) {
        this.menjannaziv = menjannaziv;
    }
}
