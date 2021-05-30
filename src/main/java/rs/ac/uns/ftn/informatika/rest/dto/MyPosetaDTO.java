package rs.ac.uns.ftn.informatika.rest.dto;

public class MyPosetaDTO {

    private String pacijentIme;
    private String pacijentPrezime;
    private String datum;
    private String vreme;
    private int trajanje;
    private String dijagnoza;
    private String zaposleni;

    public MyPosetaDTO() {
    }

    public MyPosetaDTO(String pacijentIme, String pacijentPrezime, String datum, String vreme, int trajanje, String dijagnoza, String zaposleni) {
        this.pacijentIme = pacijentIme;
        this.pacijentPrezime = pacijentPrezime;
        this.datum = datum;
        this.vreme = vreme;
        this.trajanje = trajanje;
        this.dijagnoza = dijagnoza;
        this.zaposleni = zaposleni;
    }

    public String getPacijentIme() {
        return pacijentIme;
    }

    public void setPacijentIme(String pacijentIme) {
        this.pacijentIme = pacijentIme;
    }

    public String getPacijentPrezime() {
        return pacijentPrezime;
    }

    public void setPacijentPrezime(String pacijentPrezime) {
        this.pacijentPrezime = pacijentPrezime;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public String getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(String zaposleni) {
        this.zaposleni = zaposleni;
    }


}
