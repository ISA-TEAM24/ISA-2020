package rs.ac.uns.ftn.informatika.rest.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.h2.engine.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import rs.ac.uns.ftn.informatika.rest.dto.UserRequest;
import rs.ac.uns.ftn.informatika.rest.model.Korisnik;
import rs.ac.uns.ftn.informatika.rest.security.auth.JwtAuthenticationRequest;
import rs.ac.uns.ftn.informatika.rest.service.KorisnikService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KorisnikService userService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Rollback(true)
    @Transactional
    public void signUpUser() throws Exception{
        // expected pw hash

        UserRequest k = new UserRequest();
        k.setPassword("test");
        k.setUsername("test");
        k.setFirstname("ime");
        k.setLastname("prezime");
        k.setAddress("adresa");
        k.setPhone("phonenum");
        k.setCountry("drzava");
        k.setCity("city");
        k.setEmail("emaill");

        String signMeUp = createSignUpJSON(k);

        //mockMvc.perform(post("/auth/login").contentType(contentType).content(logMeIn)).andExpect();
        mockMvc.perform(post("/auth/signup").contentType(contentType).content(signMeUp)).andExpect(status().isCreated());
    }


    @Test(expected = NestedServletException.class)
    public void loginFalseCredentials() throws Exception {
        String logMeIn = createLoginJSON("bad", "bad");
        mockMvc.perform(post("/auth/login").contentType(contentType).content(logMeIn)).andExpect(status().is4xxClientError());

    }
    @Test
    @Rollback(true)
    //@Transactional
    public void loginCorrectCredentialsAfterSignup() throws Exception {

        UserRequest k = new UserRequest();
        k.setPassword("test");
        k.setUsername("test");
        k.setFirstname("ime");
        k.setLastname("prezime");
        k.setAddress("adresa");
        k.setPhone("phonenum");
        k.setCountry("drzava");
        k.setCity("city");
        k.setEmail("emaill");

        String signMeUp = createSignUpJSON(k);

        mockMvc.perform(post("/auth/signup").contentType(contentType).content(signMeUp));

        String logMeIn = createLoginJSON("test", "test");
        mockMvc.perform(post("/auth/login").contentType(contentType).content(logMeIn)).andExpect(status().isOk());
    }



    private String createLoginJSON(String username, String password) throws JsonProcessingException {

        JwtAuthenticationRequest request = new JwtAuthenticationRequest(username, password);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }

    private String createSignUpJSON(UserRequest userRequest) throws JsonProcessingException{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(userRequest);
    }
}
