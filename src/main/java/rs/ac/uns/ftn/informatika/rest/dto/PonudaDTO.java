package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Korisnik;

import java.util.List;

public class PonudaDTO {

    private String id;
    private int ukupnaCena;
    private String posiljalac;
    private boolean admin;
    private boolean datumprosao;
    private List<NarudzbenicaLekDTO> lekovi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(int ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public String getPosiljalac() {
        return posiljalac;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDatumprosao() {
        return datumprosao;
    }

    public void setDatumprosao(boolean datumprosao) {
        this.datumprosao = datumprosao;
    }

    public void setPosiljalac(String posiljalac) {
        this.posiljalac = posiljalac;
    }

    public List<NarudzbenicaLekDTO> getLekovi() {
        return lekovi;
    }

    public void setLekovi(List<NarudzbenicaLekDTO> lekovi) {
        this.lekovi = lekovi;
    }
}
