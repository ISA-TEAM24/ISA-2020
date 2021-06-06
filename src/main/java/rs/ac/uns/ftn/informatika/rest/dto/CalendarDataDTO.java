package rs.ac.uns.ftn.informatika.rest.dto;

public class CalendarDataDTO {
    private Long posetaId;
    private String pacijentIme;
    private String pacijentPrezime;
    private String datum;
    private String vreme;
    private int trajanje;
    private String dijagnoza;
    private String zaposleni;
    private String apoteka;
    private boolean aktivan;

    public Long getPosetaId() {
        return posetaId;
    }

    public void setPosetaId(Long posetaId) {
        this.posetaId = posetaId;
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

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }
}
