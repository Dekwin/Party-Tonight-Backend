package com.partymaker.mvc.service.mail;

import com.partymaker.mvc.model.whole.UserEntity;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
public interface MailService {
    void sendResetPasswordMail(UserEntity receiver, String fromUrl) throws IOException, MessagingException;
    void sendMail(String receiverEmail, String subject, String text) throws IOException, MessagingException;
    void sendNewResetPasswordMail(UserEntity userWithUpdatedPassword) throws IOException, MessagingException;
    void sendVerifyUserMail(UserEntity receiver, String fromUrl) throws IOException, MessagingException;
}
