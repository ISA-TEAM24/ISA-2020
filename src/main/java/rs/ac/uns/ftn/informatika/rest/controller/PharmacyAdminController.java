package rs.ac.uns.ftn.informatika.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.dto.*;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Lek;
import rs.ac.uns.ftn.informatika.rest.model.Upit;
import rs.ac.uns.ftn.informatika.rest.service.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/phadmin", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyAdminController {

    @Autowired
    private PharmacyAdminService pharmacyAdminService;

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private PosetaService posetaService;

    @Autowired
    private RezervacijaService rezervacijaService;

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private LekService lekService;

    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public Korisnik user(Principal user) {
        return this.pharmacyAdminService.findByUsername(user.getName());
    }

    @PostMapping("/firstlogpwchange")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<Korisnik> firstLogPwChange(Principal p) {
        System.out.println("***" + p.getName() + "***");
        String username = p.getName();
        Korisnik user = null;
        try {
            user = this.pharmacyAdminService.changeFirstLoginState(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/editdata")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public Korisnik editUser(@RequestBody UserEditDTO user) {
        System.out.println(user);
        return this.pharmacyAdminService.editPharmacyAdmin(user);
    }

    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    @GetMapping("/getpharmacy")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public Apoteka getMyPharmacy(Principal p) {
        String username = p.getName();
        return apotekaService.getPharmacyByAdmin(username);
    }

    @PutMapping("/editPharmacy")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> editPharmacy(@RequestBody ApotekaEditDTO apotekaEditDTO) {
        if (!apotekaService.editPharmacy(apotekaEditDTO)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/getmypharmacists")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<Korisnik> getMyPharmacists(Principal p) {
        String username = p.getName();
        Apoteka a = apotekaService.getPharmacyByAdmin(username);
        List<Korisnik> pharmacists = apotekaService.findPharmacists(a);
        return pharmacists;
    }

    @GetMapping("/getmydermatologists")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<Korisnik> getMyDermatologists(Principal p) {
        String username = p.getName();
        Apoteka a = apotekaService.getPharmacyByAdmin(username);
        List<Korisnik> dermatologists = apotekaService.findDermatologists(a);
        return dermatologists;
    }

    @PutMapping(value = "/firepharmacist/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> firePharmacist(@PathVariable("username") String username) {
        if (posetaService.fireAllowed(username)) {
            apotekaService.firePharmacist(username);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/firedermatologist/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> fireDermatologist(@PathVariable("username") String username, Principal p) {
        if (posetaService.fireAllowed(username)) {
            String phAdminUsername = p.getName();
            Apoteka a = apotekaService.getPharmacyByAdmin(phAdminUsername);
            apotekaService.fireDermatologist(username, a);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/worksinmine/{username}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public boolean worksInMine(@PathVariable("username") String username, Principal p) {
        String phAdminUsername = p.getName();
        Apoteka a = apotekaService.getPharmacyByAdmin(phAdminUsername);

        return apotekaService.worksHere(username, a);
    }

    @GetMapping(value = "/getalldermatologists")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<DermatologDTO> getAllDermatologists(Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        List<Korisnik> dermatologists = dermatologistService.getAll();
        return pharmacyAdminService.createDeramtologDtos(dermatologists, a);
    }

    @PostMapping(value = "/hiredermatologist")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> hireDermatologist(@RequestBody HireDermatologistDTO hireDermatologistDTO, Principal p) {
        System.out.println(hireDermatologistDTO.getUsername());
        System.out.println(hireDermatologistDTO.getDoDatum());
        System.out.println(hireDermatologistDTO.getOdDatum());
        System.out.println(hireDermatologistDTO.getDoVreme());
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        pharmacyAdminService.hireDermatologist(hireDermatologistDTO, a);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/findmedicineswithprice")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<PharmacyMedicineDTO> findMedicineWithPriceAndAmount(Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        return apotekaService.findMedicineWithPriceAndAmount(a);
    }

    @PutMapping("/removemedicine/{medName}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> findMedicineWithPriceAndAmount(@PathVariable("medName") String medName,Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Lek lek = lekService.getLekByNaziv(medName);

        if (rezervacijaService.removeMedicineAllowed(lek.getID(), a)) {
            apotekaService.removeMedicineFromPharmacy(a, lek.getID());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getallmeds")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<LekDTO> findAllMeds(Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        return lekService.findAllMedsAndAvailabilityInMyPharmacy(a);
    }

    @PutMapping("/addmedicine/{medName}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> addMedicineToPharmacy(@PathVariable("medName") String medName, Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Lek lek = lekService.getLekByNaziv(medName);
        apotekaService.addNewMedicineInStorage(lek, a);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/checkmedicine/{medName}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> checkDoesMedicineExists(@PathVariable("medName") String medName,Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Lek lek = lekService.getLekByNaziv(medName);

        if (lek == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        apotekaService.checkDoesPharmacyHaveMedicine(a, lek);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody NarudzbenicaDTO narudzbenicaDTO, Principal p) {
        Apoteka a = apotekaService.getPharmacyByAdmin(p.getName());
        Korisnik k = pharmacyAdminService.findByUsername(p.getName());
        narudzbenicaService.createPurchaseOrder(narudzbenicaDTO, a, k);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/getunsuccessfulqueries")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public List<UpitDTO> getUnscuccessfulQueries(Principal p) {
        Apoteka apoteka = apotekaService.getPharmacyByAdmin(p.getName());
        return pharmacyAdminService.getUnscuccessfulQueries(apoteka);
    }

    @DeleteMapping("/deletequery/{id}")
    @PreAuthorize("hasRole('PH_ADMIN')")
    public ResponseEntity<?> deleteQuery(@PathVariable("id") String id) {
        Long ID = Long.parseLong(id);
        pharmacyAdminService.deleteQuery(ID);

        return new ResponseEntity<>(null,HttpStatus.OK);
    }

}