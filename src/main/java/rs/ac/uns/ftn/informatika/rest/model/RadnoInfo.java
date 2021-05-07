package rs.ac.uns.ftn.informatika.rest.model;

import javax.persistence.*;
import java.util.ArrayList;
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


}
