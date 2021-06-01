package rs.ac.uns.ftn.informatika.rest.dto;

public class PenaltyDTO {

    String email;
    String id;

    public PenaltyDTO() {
    }

    public PenaltyDTO(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
