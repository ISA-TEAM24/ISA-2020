package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.List;

public class CenovnikDTO {

    private List<CenovnikStavkeDTO> stavke;

    public List<CenovnikStavkeDTO> getStavke() {
        return stavke;
    }

    public void setStavke(List<CenovnikStavkeDTO> stavke) {
        this.stavke = stavke;
    }
}
