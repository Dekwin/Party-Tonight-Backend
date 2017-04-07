package com.partymaker.mvc.service.mail.jwt;

import com.partymaker.mvc.model.whole.UserEntity;
import org.springframework.security.core.AuthenticationException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
public interface JWTService {
    String getToken(UserEntity forUser, String purpose);
    UserEntity getUser(String forToken, String purpose) throws AuthenticationException;


}
