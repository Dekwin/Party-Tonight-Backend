package com.partymaker.mvc.admin.confirmation.mail;

import com.partymaker.mvc.dao.admin.AdminDao;
import com.partymaker.mvc.model.admin.Admin;
import com.partymaker.mvc.model.whole.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;

/**
 * Created by anton on 13.02.17.
 */
@Service
@Transactional
public class AdminService {


    @Autowired
    AdminDao adminDao;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Value("${email.account.confirmation}")
    private String confirmationUrl;


    public void send(UserEntity email, String secret) {
        logger.info("Send to email " + email);
        gmail(email, secret);
    }


    public void saveAdminEntity(Admin admin) {
        logger.info("Creating admin " + admin);
        admin.setId_admin(1);
        adminDao.createAdmin(admin);
    }

    public Admin getAdminEntity(int id) {
        return adminDao.getAdminEntity(id);
    }

    public void gmail(UserEntity user, String secret) {


        Admin admin = adminDao.getAdminEntity(1);

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
                        return new PasswordAuthentication(admin.getEmailUserName(), admin.getEmailUserPassword());
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(admin.getAccountEmail()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Testing Subject");
            message.setText("Pleas confirm your account by this link : " + confirmationUrl + "secret=" + secret + "&account_id=" + user.getId_user());

            Transport.send(message);

            logger.info("Confirmation has been sent");

        } catch (MessagingException e) {
            logger.error("Error sending email confirmation due to ", e);
            throw new RuntimeException(e);
        }
    }
}
