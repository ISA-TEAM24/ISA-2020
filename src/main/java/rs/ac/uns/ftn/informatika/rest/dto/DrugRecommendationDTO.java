package rs.ac.uns.ftn.informatika.rest.dto;

public class DrugRecommendationDTO {
    private Long apotekaId;
    private String pacijentEmail;

    public DrugRecommendationDTO() {
    }

    public DrugRecommendationDTO(Long apotekaId, String pacijentEmail) {
        this.apotekaId = apotekaId;
        this.pacijentEmail = pacijentEmail;
    }

    public Long getApotekaId() {
        return apotekaId;
    }

    public void setApotekaId(Long apotekaId) {
        this.apotekaId = apotekaId;
    }

    public String getPacijentEmail() {
        return pacijentEmail;
    }

    public void setPacijentEmail(String pacijentEmail) {
        this.pacijentEmail = pacijentEmail;
    }
}
