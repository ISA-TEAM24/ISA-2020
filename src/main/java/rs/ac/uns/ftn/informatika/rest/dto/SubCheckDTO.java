package rs.ac.uns.ftn.informatika.rest.dto;

public class SubCheckDTO {

    private String naziv;
    private boolean prati;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isPrati() {
        return prati;
    }

    public void setPrati(boolean prati) {
        this.prati = prati;
    }
}
