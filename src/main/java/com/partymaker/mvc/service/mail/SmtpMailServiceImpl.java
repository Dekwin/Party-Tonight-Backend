package com.partymaker.mvc.service.mail;

import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.admin.AdminServiceImpl;
import com.partymaker.mvc.service.mail.jwt.JWTService;
import com.partymaker.mvc.service.mail.jwt.JWTServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
@Service("smtpMailService")
public class SmtpMailServiceImpl implements MailService {

    private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);

    private final String MAIL_SMTP_HOST = "mail.smtp.host";
    private final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final String MAIL_SMTP_PORT = "mail.smtp.port";
    private final String MAIL_SMTP_USERNAME = "mail.smtp.username";
    private final String MAIL_SMTP_PASSWORD = "mail.smtp.password";

    private final String MAIL_PROPERTIES_PATH = "mail.properties";

    @Value("${email.account.confirmation}")
    private String verifyUserUrl;

    @Value("${account.resetPasswordUrl}")
    private String resetPasswordUrl;

    @Autowired
    private JWTService jwtService;

    private Properties getMailProps() throws IOException {
        Properties prop = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTIES_PATH);//new FileInputStream(MAIL_PROPERTIES_PATH);
        prop.load(in);
        in.close();
        return prop;
    }


    private boolean saveMailProps(Properties properties) throws IOException {



        OutputStream out = new FileOutputStream(MAIL_PROPERTIES_PATH);
        properties.store(out, "");
        out.close();
        return true;

    }


    private boolean setMailConfig(MailConfigEntity mailConfigEntity) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");

        try {
            Properties props = getMailProps();
            props.put(MAIL_SMTP_HOST, mailConfigEntity.getHost());
            props.put(MAIL_SMTP_SOCKETFACTORY_PORT, mailConfigEntity.getPort());
            props.put(MAIL_SMTP_SOCKETFACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
            props.put(MAIL_SMTP_AUTH, "true");
            props.put(MAIL_SMTP_PORT, mailConfigEntity.getPort());
            props.put(MAIL_SMTP_USERNAME, mailConfigEntity.getUsername());
            props.put(MAIL_SMTP_PASSWORD, mailConfigEntity.getPassword());
            saveMailProps(props);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    private MailConfigEntity getMailConfig(Properties fromProps) {
        MailConfigEntity mailConfigEntity = new MailConfigEntity();
        mailConfigEntity.setHost(fromProps.getProperty(MAIL_SMTP_HOST));
        mailConfigEntity.setPort(fromProps.getProperty(MAIL_SMTP_SOCKETFACTORY_PORT));
        mailConfigEntity.setUsername(fromProps.getProperty(MAIL_SMTP_USERNAME));
        mailConfigEntity.setPassword(fromProps.getProperty(MAIL_SMTP_PASSWORD));
        return mailConfigEntity;
    }

    public void sendMail(String receiverEmail, String subject, String text) throws IOException, MessagingException {

        Properties properties = null;
        try {
            properties = getMailProps();


            MailConfigEntity mailConfigEntity = getMailConfig(properties);

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailConfigEntity.getUsername(), mailConfigEntity.getPassword());
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailConfigEntity.getUsername()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            logger.info("Confirmation has been sent");

        } catch (MessagingException e) {
            logger.error("Error sending email due to ", e);
            throw e;

        } catch (IOException e) {

            logger.error("Error sending email due to ", e);
            throw e;
        }
    }

    @Override
    public void sendResetPasswordMail(UserEntity receiver) throws IOException, MessagingException {
        String token = jwtService.getToken(receiver, JWTServiceImpl.TOKEN_PURPOSE_RESET_PASSWORD);
        sendMail(receiver.getEmail(),
                "Testing Subject",
                "Reset password for account " + receiver.getEmail() + " by this link : "
                        + resetPasswordUrl + "token=" + token);
    }

    @Override
    public void sendNewResetPasswordMail(UserEntity userWithUpdatedPassword) throws IOException, MessagingException {
        sendMail(userWithUpdatedPassword.getEmail(), "New password for " + userWithUpdatedPassword.getEmail(), "Your new password: " + userWithUpdatedPassword.getPassword());
    }


    @Override
    public void sendVerifyUserMail(UserEntity receiver) throws IOException, MessagingException {

        String token = jwtService.getToken(receiver, JWTServiceImpl.TOKEN_PURPOSE_VERIFY_USER);
        sendMail(receiver.getEmail(),
                "Testing Subject",
                "Please confirm your account by this link : "
                        + verifyUserUrl + "token=" + token);

    }

}
