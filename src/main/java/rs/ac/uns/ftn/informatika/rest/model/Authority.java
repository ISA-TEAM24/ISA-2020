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

    public Authority() {
    }

    public Authority(Long ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Authority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
