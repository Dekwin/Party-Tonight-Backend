package com.partymaker.mvc.service.admin;

import com.partymaker.mvc.model.whole.UserEntity;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
public interface AdminService {
    void saveUserAsAdmin(UserEntity admin);
    void sendTokenToResetPassword(String email) throws IOException, MessagingException;
    void verifyTokenToResetPassword(String token) throws IOException, MessagingException;
    void verifyUserByToken(String token) throws IOException, MessagingException;
}
