package rs.ac.uns.ftn.informatika.rest.dto;

public class MedicineDTO {

    private Long ID;
    private String naziv;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
