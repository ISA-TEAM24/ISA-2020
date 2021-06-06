package rs.ac.uns.ftn.informatika.rest.model;

import org.hibernate.type.EntityType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Terapija {
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Long ID;
    @OneToMany(mappedBy = "terapija", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TherapyItem> lekovi = new HashSet<TherapyItem>();
    @Column(nullable = false)
    int brojDana;


}
