package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class RadnoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @ElementCollection
    private List<Integer> neradniDani;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Period> businessHours;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<Integer> getNeradniDani() {
        return neradniDani;
    }

    public void setNeradniDani(List<Integer> neradniDani) {
        this.neradniDani = neradniDani;
    }

    public List<Period> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(List<Period> businessHours) {
        this.businessHours = businessHours;
    }

    @Override
    public String toString() {
        return "RadnoInfo{" +
                "ID=" + ID +
                ", neradniDani=" + neradniDani +
                ", businessHours=" + businessHours +
                '}';
    }

    public boolean isDateInRange(Date date, LocalTime time) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("DAY OF THE WEEK IS " + dayOfWeek);
        if (this.neradniDani.contains(dayOfWeek)) {
            System.out.println("FARMACEUT NE RADI " + dayOfWeek);
            System.out.println(date.toString());
            return false;
        }
        //pretpostavicemo da farmaceut ne radi za to timestamp koji je poslat
        boolean isWorking = false;

        for (Period p : this.businessHours) {
            System.out.println("Comparing " + p.getOdDatum() + " to " + date);

            if (p.getOdDatum().compareTo(date) > 0){
                System.out.println("AAA");
                continue;
            }

            System.out.println("Comparing " + p.getDoDatum() + " to " + date);
            if (p.getDoDatum().compareTo(date) < 0) {

                System.out.println("BBB");
                continue;
            }
            System.out.println("Comparing " + p.getDoVreme() + " to " + time);
            if (p.getOdVreme().compareTo(time) > 0){
                System.out.println("CCC");
                continue;
            }

            System.out.println("Comparing " + p.getOdVreme() + " to " + time);
            if (p.getDoVreme().compareTo(time) < 0) {
                System.out.println("DDD");
                continue;
            }
            isWorking = true;

        }
        return isWorking;
    }
}
