package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public boolean sendSimpleMessage(String to, String subject, String text) {

        boolean retBool = true; // assume success
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@mdnn.com");
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


}
