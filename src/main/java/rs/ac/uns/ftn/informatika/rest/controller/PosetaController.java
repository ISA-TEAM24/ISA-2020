package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;
import rs.ac.uns.ftn.informatika.rest.service.PosetaService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @GetMapping("/visit/getfinishedvisits")
    public List<MyPosetaDTO> getFinished(Principal p) {
        List<Poseta> getList = posetaService.getFinishedPoseteByDermOrPharm(p.getName());
        List<MyPosetaDTO> retList = new ArrayList<>();

        for(Poseta po : getList) {

            String imepac = po.getPacijent().getIme();
            String prezpac = po.getPacijent().getPrezime();
            String datum = po.getDatum().toString().split(" ")[0];
            String vreme = po.getVreme().toString();
            int trajanje = po.getTrajanje();
            String dijagnoza = po.getDijagnoza();
            String zaposleni = po.getZaposleni().getIme() + " " + po.getZaposleni().getPrezime();

            retList.add(new MyPosetaDTO(imepac, prezpac, datum, vreme, trajanje, dijagnoza,zaposleni));
        }

        return retList;
    }

    @PreAuthorize("hasRole('PH_ADMIN')")
    @PostMapping("/addnewappointment")
    public ResponseEntity<?> addNewDermatologistAppointmentPhAdmin(@RequestBody NewPosetaDTO dto, Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        posetaService.createNewAppointment(dto, a);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @PostMapping("/consult/schedule")
    public ResponseEntity<String> scheduleConsultByPharmacist(@RequestBody ScheduleDTO dto, Principal pharmacist) throws ParseException {

        System.out.println(new Date() + "___________________________________");
        int answer = posetaService.scheduleConsultByPharmacist(pharmacist.getName(), dto);

        if(answer == 1) {
            return new ResponseEntity("CREATED", HttpStatus.CREATED);
        }
        else if(answer == 2 ) {
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/exams/add")
    public ResponseEntity addExam(@RequestBody IdDTO dto, Principal p) throws ParseException {

        Long appID = Long.parseLong(dto.getId());
        boolean success = posetaService.addExam(appID, p.getName());

        if (!success) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(null, HttpStatus.CREATED);

    }

}
