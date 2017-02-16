package com.partymaker.mvc.service.confirmation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.confirmation.mail.MailService;
import com.partymaker.mvc.service.confirmation.opt.Otp;
import com.partymaker.mvc.service.confirmation.opt.model.Secret;
import com.partymaker.mvc.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by anton on 13.02.17.
 */
@Service
public class ConfirmationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationService.class);

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    Otp otpService;

    public void confirm(String otp) throws JsonProcessingException {
        if (!otpService.validate(otp))
            throw new RuntimeException("Expired secret");

//        userService.userUnLock();
    }

    public void verify(String user_id) {
        logger.info("Verify user with id " + user_id);
        UserEntity e = (UserEntity) userService.findUserBuId(Long.parseLong(user_id));
        if (e == null || e.getEmail() == null || e.getEmail().equals(""))
            throw new RuntimeException("Bad user to verify");

        mailService.gmail(e.getEmail(), (String) otpService.generate(new Secret(user_id)));
    }
}
