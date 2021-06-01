package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaSafeDTO;
import rs.ac.uns.ftn.informatika.rest.dto.ApotekaWithExamsDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

   @Autowired
   private ApotekaService apotekaService;

    @GetMapping("/open/pharmacies")
    public List<ApotekaSafeDTO> getAllPharmacies() {
        System.out.println(new Date());
        return apotekaService.findAllPharmaciesLimitInfo();
    }

}
