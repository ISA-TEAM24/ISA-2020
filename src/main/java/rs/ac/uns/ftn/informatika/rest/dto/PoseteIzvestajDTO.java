package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.Map;

public class PoseteIzvestajDTO {

    private String mesec;
    private int brojPregleda;

    public String getMesec() {
        return mesec;
    }

    public void setMesec(String mesec) {
        this.mesec = mesec;
    }

    public int getBrojPregleda() {
        return brojPregleda;
    }

    public void setBrojPregleda(int brojPregleda) {
        this.brojPregleda = brojPregleda;
    }
}