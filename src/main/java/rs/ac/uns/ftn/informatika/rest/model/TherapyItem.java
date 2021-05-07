package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class TherapyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private String nazivLeka;
    @Column(nullable = false)
    private String note;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Terapija terapija;
}
