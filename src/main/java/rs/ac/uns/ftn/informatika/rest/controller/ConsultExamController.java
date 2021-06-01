package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.ConsultExamDTO;
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

        return dto;
    }
}
