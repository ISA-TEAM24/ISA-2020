package rs.ac.uns.ftn.informatika.rest.dto;

public class PrescriptionDTO {

    private String email;
    private int trajanje;
    private Long lekId;
    private Long apotekaId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Long getLekId() {
        return lekId;
    }

    public void setLekId(Long lekId) {
        this.lekId = lekId;
    }

    public Long getApotekaId() {
        return apotekaId;
    }

    public void setApotekaId(Long apotekaId) {
        this.apotekaId = apotekaId;
    }

}
