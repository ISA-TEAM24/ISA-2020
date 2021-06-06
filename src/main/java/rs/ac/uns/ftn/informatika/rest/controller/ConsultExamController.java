package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Poseta;
import rs.ac.uns.ftn.informatika.rest.service.ApotekaService;
import rs.ac.uns.ftn.informatika.rest.service.ConsultExamService;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsultExamController {

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ConsultExamService consultExamService;

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @GetMapping("/visit/getupcomings")
    public List<ConsultExamDTO> getUpcoming(Principal p) {
        List<Poseta> upcomingVisits = consultExamService.findUpcomingVisitsForEmployee(p.getName());
        List<ConsultExamDTO> dtos = new ArrayList<>();

        for(Poseta po : upcomingVisits) {
            ConsultExamDTO dto = new ConsultExamDTO();
            dto.setId(po.getID().toString());
            dto.setIme(po.getPacijent().getIme());
            dto.setPrezime(po.getPacijent().getPrezime());
            dto.setDatum(po.getDatum().toString().split(" ")[0]);
            dto.setVreme(po.getVreme().toString());
            dto.setEmail(po.getPacijent().getEmail());
            dto.setTelefon(po.getPacijent().getTelefon());
            dto.setAlergije(korisnikService.findAllergiesForUser(po.getPacijent().getUsername()));
            dto.setDijagnoza("");
            dto.setApoteka(po.getApoteka().getNaziv());
            dto.setTrajanje(po.getTrajanje());

            dtos.add(dto);
        }

        return dtos;
    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PutMapping("/visit/savereport")
    public Poseta saveReport(@RequestBody ConsultExamDTO dto) {
        Poseta p = consultExamService.savePosetaReport(Long.parseLong(dto.getId()), dto.getDijagnoza());

        return p;
    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/visit/findVisit")
    public ConsultExamDTO findPoseta(@RequestBody String id) {
        ConsultExamDTO dto = new ConsultExamDTO();

        Poseta p = consultExamService.getPosetaByID(id);

        dto.setId(p.getID().toString());
        dto.setIme(p.getPacijent().getIme());
        dto.setPrezime(p.getPacijent().getPrezime());
        dto.setDatum(p.getDatum().toString().split(" ")[0]);
        dto.setVreme(p.getVreme().toString());
        dto.setEmail(p.getPacijent().getEmail());
        dto.setTelefon(p.getPacijent().getTelefon());
        dto.setAlergije(korisnikService.findAllergiesForUser(p.getPacijent().getUsername()));
        dto.setDijagnoza("");
        dto.setApoteka(p.getApoteka().getNaziv());
        dto.setApotekaId(p.getApoteka().getID());

        return dto;
    }

    @PreAuthorize("hasAnyRole('PHARMACIST', 'DERMATOLOGIST')")
    @PostMapping("/visit/givePenalty")
    public ResponseEntity givePenalty(@RequestBody PenaltyDTO dto) {

        if(consultExamService.addPenalToPatient(dto.getEmail())) {
            consultExamService.removePatientFromVisit(dto.getId());

            return new ResponseEntity(null, HttpStatus.OK);

        }

        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @PostMapping("/visit/predefinedtermins")
    public ResponseEntity<List<PredefExamDTO>> findPredefinedTerminsByDerm(@RequestBody IdDTO dto) {

        List<PredefExamDTO> retList = new ArrayList<>();
        List<Poseta> predefined = consultExamService.findPredefinedVisitsForDermatologist(Long.parseLong(dto.getId()));

        for(Poseta p : predefined) {
            PredefExamDTO newdto = new PredefExamDTO();
            newdto.setDate(p.getDatum().toString());
            newdto.setTime(p.getVreme().toString());
            newdto.setIdexam(p.getID());

            retList.add(newdto);
        }

        return new ResponseEntity<>(retList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @GetMapping("/dermatologist/visits/all/{id}")
    public List<CalendarDataDTO> getVisitsForDermatologist(Principal p, @PathVariable Long id) {
        return consultExamService.getAllVisitsForUser(p.getName(), id);
    }

}
