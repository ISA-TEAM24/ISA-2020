package rs.ac.uns.ftn.informatika.rest.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false, unique = false)
    private String name;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Korisnik korisnik;

    @Override
    public String getAuthority() {
        return name;
    }
}
