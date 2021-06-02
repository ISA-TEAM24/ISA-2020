package rs.ac.uns.ftn.informatika.rest.controller;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.DrugAvabDTO;
import rs.ac.uns.ftn.informatika.rest.dto.DrugRecommendationDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PrescriptionDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Lek;
import rs.ac.uns.ftn.informatika.rest.service.ReceptServis;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceptController {

    @Autowired
    private ReceptServis receptServis;

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/findRecommendedMedicines")
    public List<Lek> findRecommendedMedicines(@RequestBody DrugRecommendationDTO dto) {
        return receptServis.findRecommendedMedicines(dto);
    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/checkDrugAvailabity")
    public ResponseEntity checkDrugAvailabity(@RequestBody DrugAvabDTO dto) {

        if(receptServis.checkDrugAvabInPharmacy(dto)) {
            return new ResponseEntity(null, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity(null, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/getalternativedrugs")
    public ResponseEntity getAlternativeDrugs(@RequestBody DrugAvabDTO dto) {

        List<Lek> retList = receptServis.getAlternativeDrugs(dto, true);

        return new ResponseEntity(retList, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/writeprescription")
    public ResponseEntity writePrescription(@RequestBody PrescriptionDTO dto) {

        if(receptServis.writePrescription(dto)) {
            return new ResponseEntity(null, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
