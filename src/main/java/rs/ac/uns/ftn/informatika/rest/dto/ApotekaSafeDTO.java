package rs.ac.uns.ftn.informatika.rest.dto;

public class ApotekaSafeDTO {

    private String naziv;
    private String adresa;
    private float ocena;
    private int brojFarmaceuta;
    private int brojDermatologa;
    private Long ID;

    public int getBrojFarmaceuta() {
        return brojFarmaceuta;
    }

    public void setBrojFarmaceuta(int brojFarmaceuta) {
        this.brojFarmaceuta = brojFarmaceuta;
    }

    public int getBrojDermatologa() {
        return brojDermatologa;
    }

    public void setBrojDermatologa(int brojDermatologa) {
        this.brojDermatologa = brojDermatologa;
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

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
