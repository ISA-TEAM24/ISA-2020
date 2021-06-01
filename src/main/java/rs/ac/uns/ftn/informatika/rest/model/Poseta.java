package rs.ac.uns.ftn.informatika.rest.model;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Poseta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column
    private LocalTime vreme;
    @Column
    private Date datum;
    @Column
    private int trajanje;
    @ManyToOne
    private Korisnik pacijent;
    @ManyToOne
    private Korisnik zaposleni;
    @Column
    private String dijagnoza;
    @ManyToOne
    private Apoteka apoteka;
    @Column(nullable = false)
    private VrstaPosete vrsta;
    @Column(nullable = false)
    private int poeni;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalTime vreme) {
        this.vreme = vreme;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Korisnik getPacijent() {
        return pacijent;
    }

    public void setPacijent(Korisnik pacijent) {
        this.pacijent = pacijent;
    }

    public Korisnik getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Korisnik zaposleni) {
        this.zaposleni = zaposleni;
    }

    public String getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(String dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public Apoteka getApoteka() {
        return apoteka;
    }

    public void setApoteka(Apoteka apoteka) {
        this.apoteka = apoteka;
    }

    public VrstaPosete getVrsta() {
        return vrsta;
    }

    public void setVrsta(VrstaPosete vrsta) {
        this.vrsta = vrsta;
    }

    public int getPoeni() {
        return poeni;
    }

    public void setPoeni(int poeni) {
        this.poeni = poeni;
    }

    public boolean isSlotTaken(Date date, LocalTime time) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String datumPosete = dateFormat.format(this.datum);
        String zeljeniDatum = dateFormat.format(date);
        // ako zeljeni datum i vec zauzet datum nisu isti ne moze biti zauzet
        System.out.println(datumPosete + " ++++++++++++++++++++++++ " + zeljeniDatum);
        if (!datumPosete.equalsIgnoreCase(zeljeniDatum))
            return false;

        LocalTime kraj = this.vreme.plusMinutes(this.trajanje);
        // isti je datum, ako je zeljeno vreme unutar trajanja pregleda
        System.out.println("***ZELJENO VREME******POCETAK***KRAJ***");
        System.out.println(time.toString());
        System.out.println(this.vreme.toString());
        System.out.println(kraj.toString());

        System.out.println("*****************");
        if (time.isAfter(this.vreme) && time.isBefore(kraj))
            return true;

        if (time.compareTo(this.vreme) == 0 || time.compareTo(kraj) == 0)
            return true;
        // inace nije zauzet
        return false;
    }

    public enum VrstaPosete {
        SAVETOVANJE,
        PREGLED
    }
    public VrstaPosete getSAVETOVANJE() {
        return VrstaPosete.SAVETOVANJE;
    }

    public VrstaPosete getPREGLED() {
        return  VrstaPosete.PREGLED;
    }
}



