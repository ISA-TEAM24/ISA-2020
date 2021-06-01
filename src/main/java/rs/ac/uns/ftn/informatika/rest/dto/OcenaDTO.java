package rs.ac.uns.ftn.informatika.rest.dto;

public class OcenaDTO {

    private Long recipientID;
    private String vrstaPrimaoca;
    private float ocena;
    private String note;

    public Long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Long recipientID) {
        this.recipientID = recipientID;
    }

    public String getVrstaPrimaoca() {
        return vrstaPrimaoca;
    }

    public void setVrstaPrimaoca(String vrstaPrimaoca) {
        this.vrstaPrimaoca = vrstaPrimaoca;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
