package rs.ac.uns.ftn.informatika.rest.dto;

public class RezervacijaDTO {

    private Long lek;
    private String apoteka;
    private String rokZaPreuzimanje;


    public Long getLek() {
        return lek;
    }

    public void setLek(Long lek) {
        this.lek = lek;
    }

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public String getRokZaPreuzimanje() {
        return rokZaPreuzimanje;
    }

    public void setRokZaPreuzimanje(String rokZaPreuzimanje) {
        this.rokZaPreuzimanje = rokZaPreuzimanje;
    }
}
