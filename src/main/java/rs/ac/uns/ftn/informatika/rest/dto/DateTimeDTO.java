package rs.ac.uns.ftn.informatika.rest.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class DateTimeDTO {

    private String date;
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    // 2021-06-17**23:53
    public Date parseDateStringToDate() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",  Locale.ENGLISH);
        format.setLenient(false);
        System.out.println(">>>>>>>>>>>>>PARSED>>>>>>>>>>>>>>>>>>>");
        System.out.println(format.parse(this.date));
        System.out.println(">>>>>>>>>>>>>>STRING WAS>>>>>>>>>>>>>>>>");
        System.out.println(this.date);

        return format.parse(this.date);
    }

    public LocalTime parseTimeStringToLocalTime() {
        return LocalTime.parse(this.time);
    }
}
