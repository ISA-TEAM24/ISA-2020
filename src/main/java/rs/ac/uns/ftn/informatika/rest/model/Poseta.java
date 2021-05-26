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

    public boolean isSlotTaken(Date date, LocalTime time) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        String datumPosete = dateFormat.format(this.datum);
        String zeljeniDatum = dateFormat.format(date);
        // ako zeljeni datum i vec zauzet datum nisu isti ne moze biti zauzet
        if (!datumPosete.equalsIgnoreCase(zeljeniDatum))
            return false;

        LocalTime kraj = this.vreme.plusMinutes(this.trajanje);
        // isti je datum, ako je zeljeno vreme unutar trajanja pregleda
        if (time.isAfter(this.vreme) && time.isBefore(kraj))
            return true;
        // inace nije zauzet
        return false;
    }

}

enum VrstaPosete {
    SAVETOVANJE,
    PREGLED
}
