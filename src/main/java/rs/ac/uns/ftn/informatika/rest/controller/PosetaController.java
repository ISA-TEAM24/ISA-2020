package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.dto.AllergiesDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithExamsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.DateTimeDTO;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PosetaController {

    @Autowired
    ApotekaService apotekaService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/consult/check")
    public List<ApotekaWithExamsDTO> findPharmaciesWithFreePharmacists(@RequestBody DateTimeDTO dto) throws ParseException {
        System.out.println("received dto" + dto.getDate() + " *** " + dto.getTime());
        return this.apotekaService.findApotekaWithFreePharmacist
                (dto.parseDateStringToDate(), dto.parseTimeStringToLocalTime());
    }
}
