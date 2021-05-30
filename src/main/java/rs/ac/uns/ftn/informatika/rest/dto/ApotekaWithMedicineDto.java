package rs.ac.uns.ftn.informatika.rest.dto;

public class ApotekaWithMedicineDto {

    private String apoteka;
    private String adresa;
    private String lek;
    private Long idLeka;

    public ApotekaWithMedicineDto() {
    }

    public ApotekaWithMedicineDto(String apoteka, String adresa, String lek, Long idLeka) {
        this.apoteka = apoteka;
        this.adresa = adresa;
        this.lek = lek;
        this.idLeka = idLeka;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public String getLek() {
        return lek;
    }

    public void setLek(String lek) {
        this.lek = lek;
    }

    public Long getIdLeka() {
        return idLeka;
    }

    public void setIdLeka(Long idLeka) {
        this.idLeka = idLeka;
    }
}
