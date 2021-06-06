package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;

@Entity
public class TherapyItem {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @Column(nullable = false)
    private String nazivLeka;
    @Column(nullable = false)
    private String note;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Terapija terapija;
}
