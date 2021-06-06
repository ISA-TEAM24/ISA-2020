package rs.ac.uns.ftn.informatika.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class AllergiesDTO {

    private List<String> allergies;

    public AllergiesDTO() {
        allergies = new ArrayList<String>();
    }

    public AllergiesDTO(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
