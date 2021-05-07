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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToMany(mappedBy = "terapija", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TherapyItem> lekovi = new HashSet<TherapyItem>();
    @Column(nullable = false)
    int brojDana;


}
