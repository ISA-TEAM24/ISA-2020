package rs.ac.uns.ftn.informatika.rest.dto;

public class ConsultExamDTO {

    private String id;
    private String ime;
    private String prezime;
    private String datum;
    private String vreme;
    private String email;
    private String telefon;
    private AllergiesDTO alergije;
    private String dijagnoza;
    private String apoteka;
    private Long apotekaId;
    private int trajanje;

    public ConsultExamDTO() {

    }

    public ConsultExamDTO(String id, String ime, String prezime, String datum, String vreme, String email, String telefon, AllergiesDTO alergije, String dijagnoza, String apoteka, Long apotekaId) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.datum = datum;
        this.vreme = vreme;
        this.email = email;
        this.telefon = telefon;
        this.alergije = alergije;
        this.dijagnoza = dijagnoza;
        this.apoteka = apoteka;
        this.apotekaId = apotekaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public AllergiesDTO getAlergije() {
        return alergije;
    }

    public void setAlergije(AllergiesDTO alergije) {
        this.alergije = alergije;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public String getApoteka() {
        return apoteka;
    }

    public void setApoteka(String apoteka) {
        this.apoteka = apoteka;
    }

    public Long getApotekaId() {
        return apotekaId;
    }

    public void setApotekaId(Long apotekaId) {
        this.apotekaId = apotekaId;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }
}
