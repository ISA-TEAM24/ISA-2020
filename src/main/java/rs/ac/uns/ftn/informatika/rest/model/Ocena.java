package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private Long recipientID;
    @OneToOne
    private Korisnik od;
    @Column(nullable = false)
    private VrstaPrimaoca vrstaPrimaoca;
    @Column(nullable = false)
    private float ocena;
    @Column(nullable = false)
    private String note;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Long recipientID) {
        this.recipientID = recipientID;
    }

    public Korisnik getOd() {
        return od;
    }

    public void setOd(Korisnik od) {
        this.od = od;
    }

    public VrstaPrimaoca getVrstaPrimaoca() {
        return vrstaPrimaoca;
    }

    public void setVrstaPrimaoca(VrstaPrimaoca vrstaPrimaoca) {
        this.vrstaPrimaoca = vrstaPrimaoca;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public enum VrstaPrimaoca {
        OSOBA,
        LEK,
        APOTEKA
    }
}
