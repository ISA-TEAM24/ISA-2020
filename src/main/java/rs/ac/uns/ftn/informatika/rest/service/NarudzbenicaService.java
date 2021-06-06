package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.dto.NarudzbenicaDTO;
import rs.ac.uns.ftn.informatika.rest.dto.NarudzbenicaLekDTO;
import rs.ac.uns.ftn.informatika.rest.dto.OrderWithDetailsDTO;
import rs.ac.uns.ftn.informatika.rest.dto.OrdersDTO;
import rs.ac.uns.ftn.informatika.rest.model.Apoteka;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.model.Narudzbenica;
import rs.ac.uns.ftn.informatika.rest.model.Ponuda;
import rs.ac.uns.ftn.informatika.rest.repository.LekRepository;
import rs.ac.uns.ftn.informatika.rest.repository.NarudzbenicaRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NarudzbenicaService {

    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;

    @Autowired
    private PonudaService ponudaService;

    public void createPurchaseOrder(NarudzbenicaDTO narudzbenicaDTO, Apoteka apoteka, Korisnik korisnik) {
        Narudzbenica narudzbenica = new Narudzbenica();
        narudzbenica.setApoteka(apoteka);
        narudzbenica.setKreirao(korisnik);
        narudzbenica.setStatus(narudzbenica.getCeka());
        Map <String,Integer> spisakLekova = new HashMap<>();
        for (NarudzbenicaLekDTO lekDTO : narudzbenicaDTO.getLekovi()) {
            spisakLekova.put(lekDTO.getNaziv(), lekDTO.getKolicina());
        }
        narudzbenica.setSpisakLekova(spisakLekova);
        narudzbenica.setRokZaPonudu(narudzbenicaDTO.getRok());

        narudzbenicaRepository.save(narudzbenica);
    }

    public List<OrdersDTO> getAllOrdersFromPharmacy(Apoteka a) {
        List<Narudzbenica> narudzbenice = narudzbenicaRepository.findByApoteka(a);

        List<OrdersDTO> aktivne = new ArrayList<>();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

        for (Narudzbenica n : narudzbenice) {

            OrdersDTO dto = new OrdersDTO();
            dto.setId(n.getID().toString());
            dto.setKreirao(n.getKreirao().getUsername());
            if (n.getStatus().equals(Narudzbenica.Status.CEKA)) {
                dto.setStatus("Aktivna");
            } else {
                dto.setStatus("Obradjena");
            }
            dto.setRok(dateFormat.format(n.getRokZaPonudu()));
            List<String> lekovi = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : n.getSpisakLekova().entrySet()) {
                lekovi.add(entry.getKey());
            }
            dto.setLekovi(lekovi);
            aktivne.add(dto);
        }
        return aktivne;
    }

    public boolean deleteIfPossible(Long ID) {
        Narudzbenica n = narudzbenicaRepository.findByID(ID);
        List<Ponuda> ponude = ponudaService.findByNarudzbenica(n);
        if (ponude.isEmpty()) {
            narudzbenicaRepository.delete(n);
            return true;
        }
        return false;
    }

    public OrderWithDetailsDTO findOrderDTOByID(Long id) {
        Narudzbenica n = narudzbenicaRepository.findByID(id);
        OrderWithDetailsDTO order = new OrderWithDetailsDTO();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

        order.setUsername(n.getKreirao().getUsername());
        order.setRok(dateFormat.format(n.getRokZaPonudu()));
        List<NarudzbenicaLekDTO> dtos = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : n.getSpisakLekova().entrySet()) {
            NarudzbenicaLekDTO narudzbenicaLekDTO = new NarudzbenicaLekDTO();
            narudzbenicaLekDTO.setNaziv(entry.getKey());
            narudzbenicaLekDTO.setKolicina(entry.getValue());
            dtos.add(narudzbenicaLekDTO);
        }
        order.setLekovi(dtos);

        return order;
    }

    public Narudzbenica findOrderByID(Long id) {
        return narudzbenicaRepository.findByID(id);
    }

    public void closeOrder(Narudzbenica narudzbenica) {
        narudzbenica.setStatus(narudzbenica.getObradjena());
        narudzbenicaRepository.save(narudzbenica);
    }
}
