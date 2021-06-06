package rs.ac.uns.ftn.informatika.rest.dto;

public class PredefExamDTO {

    private String date;
    private String time;
    private Long idexam;

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

    public Long getIdexam() {
        return idexam;
    }

    public void setIdexam(Long idexam) {
        this.idexam = idexam;
    }
}
