package rs.ac.uns.ftn.informatika.rest.dto;

public class DrugAvabDTO {
    private String medId;
    private String apotekaId;
    private String patientEmail;

    public DrugAvabDTO() {
    }


    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getApotekaId() {
        return apotekaId;
    }

    public void setApotekaId(String apotekaId) {
        this.apotekaId = apotekaId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
