package rs.ac.uns.ftn.informatika.rest.dto;

public class ZalbaDTO {

    private Long idPrimaoca;
    private String vrstaPrimaoca;
    private String text;

    public Long getIdPrimaoca() {
        return idPrimaoca;
    }

    public void setIdPrimaoca(Long idPrimaoca) {
        this.idPrimaoca = idPrimaoca;
    }

    public String getVrstaPrimaoca() {
        return vrstaPrimaoca;
    }

    public void setVrstaPrimaoca(String vrstaPrimaoca) {
        this.vrstaPrimaoca = vrstaPrimaoca;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
