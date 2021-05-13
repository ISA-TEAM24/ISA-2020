package rs.ac.uns.ftn.informatika.rest.unit.Service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KorisnikServiceTest {

    @Mock
    private KorisnikRepository korisnikRepositoryMock;

    @Mock
    private Korisnik korisnikMock;

    @InjectMocks
    private KorisnikService korisnikService;
    /*
    Long ID, String email, String username, String password, Date lastPasswordResetDate, String ime,
                    String prezime, String adresa, String grad, String drzava, String telefon, boolean activated,
                    Set<Authority> authorities

    List<Authority> findAuthoritiesByID(Long ID);

    Korisnik findOneByEmail(String email);

    Korisnik findByUsername(String username);
    */

    @Test
    public void testFindAllKorisniks() {
        when(korisnikRepositoryMock.findAll()).thenReturn(Arrays.asList(new Korisnik()));

        List<Korisnik> korisnici = korisnikService.findAll();

        assertThat(korisnici).hasSize(1);

        verify(korisnikRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }

    @Test
    public void testFindKorisnikByEmailAndUsername() {

        Korisnik k = new Korisnik();
        k.setUsername("lucyxz");
        k.setEmail("gmail@gmail.com");


        when(korisnikRepositoryMock.findByUsername("lucyxz")).thenReturn(k);
        when(korisnikRepositoryMock.findOneByEmail("gmail@gmail.com")).thenReturn(k);

        assertThat(korisnikRepositoryMock.findByUsername("lucyxz"))
                .isEqualTo(korisnikRepositoryMock.findOneByEmail("gmail@gmail.com"));

        verify(korisnikRepositoryMock, times(1)).findByUsername("lucyxz");
        verify(korisnikRepositoryMock, times(1)).findOneByEmail("gmail@gmail.com");
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }

    @Test
    public void testFindAuthoritiesByID() {
        Korisnik k = new Korisnik();
        k.setID(1L);
        HashSet authorities = new HashSet();
        authorities.add(new Authority("ROLE_USER"));
        k.setAuthorities(authorities);

        when(korisnikRepositoryMock.findAuthoritiesByID(1L)).thenReturn(authorities);

        assertThat(korisnikRepositoryMock.findAuthoritiesByID(1l)).hasSize(1);

        verify(korisnikRepositoryMock, times(1)).findAuthoritiesByID(1L);
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }


}
