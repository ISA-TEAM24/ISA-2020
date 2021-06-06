package rs.ac.uns.ftn.informatika.rest.unit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.dto.UserEditDTO;
import rs.ac.uns.ftn.informatika.rest.model.Authority;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.rest.repository.KorisnikRepository;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import javax.validation.constraints.Null;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KorisnikServiceTest {

    @Mock
    private KorisnikRepository korisnikRepositoryMock;

    @Mock
    private AuthorityRepository authorityRepositoryMock;

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

        assertThat(korisnikService.findByUsername("lucyxz"))
                .isEqualTo(korisnikService.findByEmail("gmail@gmail.com"));

        verify(korisnikRepositoryMock, times(1)).findByUsername("lucyxz");
        verify(korisnikRepositoryMock, times(1)).findOneByEmail("gmail@gmail.com");
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }

    @Test
    public void testFindAuthoritiesByID() {
        Korisnik k = new Korisnik();
        k.setID(1L);

        when(authorityRepositoryMock.getOne(1L)).thenReturn(new Authority("ROLE_USER"));

        List<Authority> retList = korisnikService.findAuthorityById(k.getID());
        assertThat(retList).hasSize(1);

        verify(authorityRepositoryMock, times(1)).getOne(1L);
        verifyNoMoreInteractions(authorityRepositoryMock);
    }

    @Test(expected = NullPointerException.class)
    public void verifyNonExistingUser(){
        Korisnik k = new Korisnik();
        k.setUsername("test");
        when(korisnikRepositoryMock.findByUsername("test")).thenReturn(null);
        Korisnik k1 = korisnikService.verifyKorisnik("test");
        assertThat(k1).isNull();
        verify(korisnikRepositoryMock, times(1)).findByUsername("test");
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }

    @Test
    public void verifyExistingUser() {
        Korisnik k = new Korisnik();
        k.setUsername("test");
        when(korisnikRepositoryMock.findByUsername("test")).thenReturn(k);
        when(korisnikRepositoryMock.save(k)).thenReturn(k);
        Korisnik k1 = korisnikService.verifyKorisnik("test");
        assertThat(k1).isEqualTo(k);
        verify(korisnikRepositoryMock, times(1)).findByUsername("test");
        verify(korisnikRepositoryMock, times(1)).save(k);
        verifyNoMoreInteractions(korisnikRepositoryMock);
    }





}
