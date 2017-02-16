package com.partymaker.mvc.service.confirmation.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by anton on 13.02.17.
 */
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${email.account.user.name}")
    private String userName;

    @Value("${email.account.user.password}")
    private String userPassword;

    @Value("${email.account.confirmation}")
    private String confirmationUrl;

    @Value("${email.account.email}")
    private String accountEmail;

    public void send(String email, String secret) {
        logger.info("Send to email " + email);
        gmail(email, secret);
    }

    public void gmail(String email, String secret) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(accountEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Testing Subject");
            message.setText("Pleas confirm your account by this link " + confirmationUrl + secret);

            Transport.send(message);

            logger.info("Confirmation has been sent");

        } catch (MessagingException e) {
            logger.error("Error sending email confirmation due to ", e);
            throw new RuntimeException(e);
        }
    }
}
