package rs.ac.uns.ftn.informatika.rest.dto;

import java.time.LocalTime;
import java.util.Date;

public class NewPosetaDTO {

    private String dermatologist;
    private Date date;
    private LocalTime time;
    private Integer duration;

    public String getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(String dermatologist) {
        this.dermatologist = dermatologist;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
