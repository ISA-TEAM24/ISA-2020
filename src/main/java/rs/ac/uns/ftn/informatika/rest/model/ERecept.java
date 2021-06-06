package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ERecept {

    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @Column(nullable = false)
    private String ime;
    @Column(nullable = false)
    private String prezime;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date datumIzdavanja;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lek> lekovi;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private Long apotekaID;
    @Column(nullable = false)
    private int trajanjeTerapije;

    public ERecept(){

    }

    public Long getApotekaID() {
        return apotekaID;
    }

    public void setApotekaID(Long apotekaID) {
        this.apotekaID = apotekaID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public List<Lek> getLekovi() {
        return lekovi;
    }

    public void setLekovi(List<Lek> lekovi) {
        this.lekovi = lekovi;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTrajanjeTerapije() {
        return trajanjeTerapije;
    }

    public void setTrajanjeTerapije(int trajanjeTerapije) {
        this.trajanjeTerapije = trajanjeTerapije;
    }

    @Override
    public String toString() {
        return "ERecept{" +
                "ID=" + ID +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", email='" + email + '\'' +
                ", datumIzdavanja=" + datumIzdavanja +
                ", lekovi=" + lekovi +
                ", status=" + status +
                ", apotekaID=" + apotekaID +
                ", trajanjeTerapije=" + trajanjeTerapije +
                '}';
    }

    public enum Status {
        AKTIVAN,
        OBRADJEN,
        ODBIJEN
    }
}
