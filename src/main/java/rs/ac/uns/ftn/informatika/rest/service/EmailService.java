package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.model.Ponuda;
import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    JavaMailSender emailSender;

    public boolean sendSimpleMessage(String to, String subject, String text) {

        boolean retBool = true; // assume success
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mdnnpharm@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
            retBool = false;
        }
        return retBool;
    }

    public boolean sendReservationCreatedMessage(Rezervacija r) {

        String text = "Hello " + r.getPacijent().getUsername();
        text += ", you successfully reserved " + r.getLek().getNaziv() + " in Pharmacy " + r.getApoteka().getNaziv();
        text += ", " + r.getApoteka().getAdresa();
        text += ". Your unique reservation ID is " + r.getID() + ".";
        text += " Your due pick up date is " + r.getRokZaPreuzimanje().toString().split("T")[0];

        return sendSimpleMessage(r.getPacijent().getEmail(), "You reserved some medicine", text);
    }

    public boolean sendOfferDeniedMessage(Ponuda p) {
        String text = "Hello " + p.getPosiljalac().getUsername();
        text += ", your offer for purchase order made by " + p.getNarudzbenica().getApoteka().getNaziv() + " pharmacy ";
        text += " has been rejected. We are sorry and we hope you will make more offers for our purcahse orders in future.";

        return sendSimpleMessage(p.getPosiljalac().getEmail(), "Purchase order closed", text);
    }

    public boolean sendOfferAcceptedMessage(Ponuda p) {
        String text = "Hello " + p.getPosiljalac().getUsername();
        text += ", Congratulations! Your offer for purchase order made by " + p.getNarudzbenica().getApoteka().getNaziv() + " pharmacy ";
        text += " has been accepted. We hope you will make more offers for our purcahse orders in future.";

        return sendSimpleMessage(p.getPosiljalac().getEmail(), "Purchase order closed", text);
    }
}
