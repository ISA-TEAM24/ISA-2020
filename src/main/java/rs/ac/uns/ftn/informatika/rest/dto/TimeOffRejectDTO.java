package rs.ac.uns.ftn.informatika.rest.dto;

public class TimeOffRejectDTO {

    private String id;
    private String razlogOdbijanja;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazlogOdbijanja() {
        return razlogOdbijanja;
    }

    public void setRazlogOdbijanja(String razlogOdbijanja) {
        this.razlogOdbijanja = razlogOdbijanja;
    }
}
