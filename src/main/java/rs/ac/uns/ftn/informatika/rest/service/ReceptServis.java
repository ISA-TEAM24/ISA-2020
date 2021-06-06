package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.DrugAvabDTO;
import rs.ac.uns.ftn.informatika.rest.dto.DrugRecommendationDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PrescMedDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PrescriptionDTO;
import rs.ac.uns.ftn.informatika.rest.model.*;
import rs.ac.uns.ftn.informatika.rest.repository.EReceptRepository;
import rs.ac.uns.ftn.informatika.rest.repository.UpitRepository;

import java.util.*;

@Service
public class ReceptServis {

    @Autowired
    private ApotekaService apotekaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EReceptRepository eReceptRepository;

    @Autowired
    private UpitRepository upitRepository;

    public List<Lek> findRecommendedMedicines(DrugRecommendationDTO dto) {
        Apoteka a = apotekaService.findById(dto.getApotekaId());
        Korisnik pacijent = korisnikService.findByEmail(dto.getPacijentEmail());

        List<Lek> allDrugsInPharmacy = apotekaService.findAllMedicinesInPharmacy(a);

        Set<String> alergijePacijenta = pacijent.getAlergije();

        return new ArrayList<>(checkIfAllergic(allDrugsInPharmacy, pacijent));

    }

    public boolean checkDrugAvabInPharmacy(DrugAvabDTO dto, String username) {
        Apoteka a = apotekaService.findById(Long.parseLong(dto.getApotekaId()));

        if(a.getMagacin().get(Long.parseLong(dto.getMedId())) >= 1) {
            return true;
        }

        Korisnik admin = apotekaService.findPharmacyAdmin(a).get(0);
        Lek l = apotekaService.lekRepository.findLekByID(Long.parseLong(dto.getMedId()));

        Upit u = new Upit();
        u.setLek(l);
        u.setKolicina(1);
        u.setApoteka(a);
        u.setPosiljalac(korisnikService.findByUsername(username));
        u.setUspesan(false);
        u.setDatum(new Date());
        
        upitRepository.save(u);

        boolean notify = emailService.notifyPharmacyAdminAboutDrugDeficit(admin, a.getNaziv(), l);
        return false;
    }

    public List<Lek> getAlternativeDrugs(DrugAvabDTO dto, boolean filterByAmount) {
        Lek l = apotekaService.lekRepository.findLekByID(Long.parseLong(dto.getMedId()));
        Korisnik pacijent = korisnikService.findByEmail(dto.getPatientEmail());

        Apoteka a = apotekaService.apotekaRepository.findByID(Long.parseLong(dto.getApotekaId()));

        List<Lek> alternatives = new ArrayList<>();

        for(Long id: l.getAlternative()) {
            if(filterByAmount == true) {
                if(a.getMagacin().get(id) < 1) {
                    continue;
                }
            }
            alternatives.add(apotekaService.lekRepository.findLekByID(id));
        }
        return new ArrayList<>(checkIfAllergic(alternatives, pacijent));
    }

    public Set<Lek> checkIfAllergic(List<Lek> drugs, Korisnik k) {
        Set<Lek> retList = new HashSet<>();

        for(Lek l : drugs) {

            boolean shouldAdd = true;

            for(String alerg : k.getAlergije()) {

                if(l.getNaziv().toLowerCase().contains(alerg.toLowerCase())) {
                    shouldAdd = false;
                    continue;
                }

                if(l.getSastav().toLowerCase().contains(alerg.toLowerCase())) {
                    shouldAdd = false;
                    continue;
                }
            }
            if(shouldAdd) {
                retList.add(l);
            }
        }
        return retList;
    }

    public boolean writePrescription(PrescriptionDTO dto) {
        ERecept recept = new ERecept();
        Korisnik pacijent = korisnikService.findByEmail(dto.getEmail());
        Lek lek = apotekaService.lekRepository.findLekByID(dto.getLekId());
        Apoteka a = apotekaService.findById(dto.getApotekaId());

        List<Lek> lekovi = new ArrayList<>();
        lekovi.add(lek);

        recept.setIme(pacijent.getIme());
        recept.setPrezime(pacijent.getPrezime());
        recept.setEmail(pacijent.getEmail());
        recept.setDatumIzdavanja(new Date());
        recept.setTrajanjeTerapije(dto.getTrajanje());
        recept.setLekovi(lekovi);
        recept.setStatus(ERecept.Status.AKTIVAN);
        recept.setApotekaID(dto.getApotekaId());

        eReceptRepository.save(recept);

        // Azuriranje stanje leka u apoteci
        Map<Long, Integer> map = a.getMagacin();
        a.getMagacin().put(dto.getLekId(), map.get(dto.getLekId()) - 1);
        apotekaService.saveApoteka(a);

        return true;
    }

    public List<ERecept> findPrescsForUser(String username) {

        Korisnik k = korisnikService.findByUsername(username);
        return eReceptRepository.findAllByEmail(k.getEmail());
    }

    public List<PrescMedDTO> findPrescribedMedicine(String username) {

        Set<PrescMedDTO> retList = new HashSet<>();

        for(ERecept er : findPrescsForUser(username)) {
            for(Lek l : er.getLekovi()) {
                PrescMedDTO dto = new PrescMedDTO();
                dto.setDatumPrepisivanja(er.getDatumIzdavanja());
                dto.setNazivLeka(l.getNaziv());
                dto.setNazivApoteke(apotekaService.findById(er.getApotekaID()).getNaziv());
                retList.add(dto);
            }
        }

        return new ArrayList<PrescMedDTO>(retList);
    }


}
