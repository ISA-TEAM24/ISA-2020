package rs.ac.uns.ftn.informatika.rest.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class NewPharmacistDTO {

    private String email;
    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String adresa;
    private String grad;
    private String drzava;
    private String telefon;
    private Date odDatum;
    private Date doDatum;
    private LocalTime odVreme;
    private LocalTime doVreme;
    private List<Integer> neradniDani;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Date getOdDatum() {
        return odDatum;
    }

    public void setOdDatum(Date odDatum) {
        this.odDatum = odDatum;
    }

    public Date getDoDatum() {
        return doDatum;
    }

    public void setDoDatum(Date doDatum) {
        this.doDatum = doDatum;
    }

    public LocalTime getOdVreme() {
        return odVreme;
    }

    public void setOdVreme(LocalTime odVreme) {
        this.odVreme = odVreme;
    }

    public LocalTime getDoVreme() {
        return doVreme;
    }

    public void setDoVreme(LocalTime doVreme) {
        this.doVreme = doVreme;
    }

    public List<Integer> getNeradniDani() {
        return neradniDani;
    }

    public void setNeradniDani(List<Integer> neradniDani) {
        this.neradniDani = neradniDani;
    }
}
