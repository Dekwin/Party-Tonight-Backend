package com.partymaker.mvc.service.admin;

import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.mail.MailService;
import com.partymaker.mvc.service.mail.jwt.JWTService;
import com.partymaker.mvc.service.mail.jwt.JWTServiceImpl;
import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService{



    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;
    @Autowired
    MailService smtpMailService;

    private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);



    @Override
    public void saveUserAsAdmin(UserEntity admin) {
            logger.info("Checking user data on existing ");

            userService.isExistUserRequiredFields(admin);

            logger.info("Creating user ");
            admin.setRole(new RoleEntity(1, "ROLE_ADMIN"));
            admin.setBillingEmail(admin.getBillingEntity().getBilling_email());

            userService.saveUser(admin);
        logger.info("Creating admin " + admin);
        //admin.setId_admin(1);
    }



    @Override
    public void sendTokenToResetPassword(String email) throws IOException, MessagingException {
       UserEntity user = (UserEntity)userService.findUserByEmail(email);
        if(user != null) {
            smtpMailService.sendResetPasswordMail(user);
        }else {
            throw new BadCredentialsException("Not valid entity");
        }
    }

    @Override
    public void verifyTokenToResetPassword(String token) throws IOException, MessagingException {
       UserEntity userEntity = jwtService.getUser(token, JWTServiceImpl.TOKEN_PURPOSE_RESET_PASSWORD);
       if(userEntity!=null) {
           String newPassword = UUID.randomUUID().toString();
           userEntity.setPassword(newPassword);
           userService.updateUser(userEntity);
           smtpMailService.sendNewResetPasswordMail(userEntity);
       }else {
           logger.info("User not found");
       }

    }

    @Override
    public void verifyUserByToken(String token) throws IOException, MessagingException {
        UserEntity userEntity = jwtService.getUser(token, JWTServiceImpl.TOKEN_PURPOSE_VERIFY_USER);
        if(userEntity!=null) {
            userService.setUserVerifiedById(userEntity.getId_user());
        }else{
            logger.info("User not found");
        }

    }


//    public UserEntity getAdminEntity(int id) {
//        return userService.getAdminEntity(id);
//    }


}
