package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ERecept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private String ime;
    @Column(nullable = false)
    private String prezime;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date datumIzdavanja;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lek> lekovi;
    private Status status;

    public ERecept(){

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
                '}';
    }



    enum Status {
        AKTIVAN,
        OBRADJEN,
        ODBIJEN
    }
}
