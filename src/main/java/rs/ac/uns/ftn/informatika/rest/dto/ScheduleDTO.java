package rs.ac.uns.ftn.informatika.rest.dto;

public class ScheduleDTO {

    private String ime;
    private String prezime;
    private String email;
    private String datum;
    private int trajanje;
    private String vreme;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String ime, String prezime, String email, String datum, int trajanje, String vreme) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.datum = datum;
        this.trajanje = trajanje;
        this.vreme = vreme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }
}
