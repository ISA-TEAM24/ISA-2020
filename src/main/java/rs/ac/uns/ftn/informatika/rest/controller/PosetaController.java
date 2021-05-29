package rs.ac.uns.ftn.informatika.rest.controller;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.PosetaService;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PosetaController {

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private PosetaService posetaService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/consult/check")
    public List<ApotekaWithExamsDTO> findPharmaciesWithFreePharmacists(@RequestBody DateTimeDTO dto) throws ParseException {
        System.out.println("received dto" + dto.getDate() + " *** " + dto.getTime());
        return this.apotekaService.findApotekaWithFreePharmacist
                (dto.parseDateStringToDate(), dto.parseTimeStringToLocalTime());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/consult/add")
    public ResponseEntity addConsult(@RequestBody PosetaDTO dto, Principal p) throws ParseException {

        boolean success = posetaService.addConsult(dto, p.getName());

        if (success) return new ResponseEntity<>(null, HttpStatus.CREATED);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/consults")
    public List<Poseta> getUpcomingVisits(Principal p) throws ParseException {

        List<Poseta> retList = posetaService.findUpcomingVisitsForUser(p.getName());
        return retList;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/consults/cancel")
    public ResponseEntity cancelVisit(@RequestBody IdDTO dto) throws ParseException {
        // -1 for not existing , 1 for less than 24h, 0 for ok
        System.out.println("LOOKING FOR VISIT WITH ID" + dto.getId());
        int cancel = posetaService.cancelVisit(Long.parseLong(dto.getId()));
        if (cancel == -1)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        if (cancel == 1)
            return new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(null, HttpStatus.OK);

    }
}
