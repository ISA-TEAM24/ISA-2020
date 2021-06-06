package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.List;

public class OrdersDTO {

    private String id;
    private String rok;
    private String kreirao;
    private String status;
    private List<String> lekovi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getKreirao() {
        return kreirao;
    }

    public void setKreirao(String kreirao) {
        this.kreirao = kreirao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLekovi() {
        return lekovi;
    }

    public void setLekovi(List<String> lekovi) {
        this.lekovi = lekovi;
    }
}
