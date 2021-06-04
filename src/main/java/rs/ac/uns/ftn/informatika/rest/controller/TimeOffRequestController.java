package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.TOffDTO;
import rs.ac.uns.ftn.informatika.rest.dto.TimeOffDTO;
import rs.ac.uns.ftn.informatika.rest.dto.TimeOffRejectDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.TimeOffZahtev;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.EmailService;
import rs.ac.uns.ftn.informatika.rest.service.TimeOffRequestService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/timeoff", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeOffRequestController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/addnew")
    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    public ResponseEntity addNewTimeOffRequest(Principal user, @RequestBody TimeOffDTO dto) {
        TimeOffZahtev request = null;
        try {
            request = timeOffRequestService.saveNewTimeOffZahtev(user.getName(), dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping("/requests/all")
    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    public List<TimeOffZahtev> getAllRequests(Principal user) {

        return timeOffRequestService.getAllMyRequests(user.getName());
    }

    @GetMapping("/getrequestsbypharmacy")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<TOffDTO> getRequestsByPharmacy(Principal p)  {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        return timeOffRequestService.getAllTimeOffsByPharmacy(a);
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> acceptTimeOffRequest(@PathVariable("id") String id) {
        TimeOffZahtev timeOffZahtev = timeOffRequestService.acceptTimeOffRequest(id);
        if (emailService.sendTimeOffAccepted(timeOffZahtev))
            return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/reject")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> rejectTimeOffRequest(@RequestBody TimeOffRejectDTO timeOffRejectDTO) {
        TimeOffZahtev timeOffZahtev = timeOffRequestService.rejectTimeOffRequest(timeOffRejectDTO.getId());
        if (emailService.sendTimeOffRejected(timeOffZahtev, timeOffRejectDTO))
            return new ResponseEntity<>(null, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
