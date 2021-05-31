package rs.ac.uns.ftn.informatika.rest.dto;

public class RezervacijaInfoDTO {

    private String pacijent;
    private String lek;
    private String brojRez;

    public RezervacijaInfoDTO() {
    }

    public RezervacijaInfoDTO(String pacijent, String lek, String brojRez) {
        this.pacijent = pacijent;
        this.lek = lek;
        this.brojRez = brojRez;
    }

    public String getPacijent() {
        return pacijent;
    }

    public void setPacijent(String pacijent) {
        this.pacijent = pacijent;
    }

    public String getLek() {
        return lek;
    }

    public void setLek(String lek) {
        this.lek = lek;
    }

    public String getBrojRez() {
        return brojRez;
    }

    public void setBrojRez(String brojRez) {
        this.brojRez = brojRez;
    }


}
