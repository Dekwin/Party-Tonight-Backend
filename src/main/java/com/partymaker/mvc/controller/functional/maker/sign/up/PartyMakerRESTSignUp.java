package com.partymaker.mvc.controller.functional.maker.sign.up;

import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.billing.BillingService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * partymaker controller
 * Created by Mostipan Anton
 */

@RestController
@RequestMapping(value = {"/maker"})
public class PartyMakerRESTSignUp {

    private static final Logger logger = Logger.getLogger(PartyMakerRESTSignUp.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date;
    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("BillingService")
    BillingService billingService;


    @PostMapping(value = {"/signup"})
    public Callable<ResponseEntity<?>> signUp(@RequestBody UserEntity user) {
        return () -> {
            logger.info("Sign up maker = " + user);
            try {
                if (user == null) {
                    return new ResponseEntity<Object>("User cannot be null", HttpStatus.BAD_REQUEST);
                } else if (user.getPassword() == null || user.getEmail() == null) {
                    return new ResponseEntity<Object>("User fields cannot be null.", HttpStatus.BAD_REQUEST);
                }
                ResponseEntity responseEntity = userService.isExistUserRequiredFields(user);
                if (responseEntity != null) {
                    logger.info("Bad user data");
                    return responseEntity;
                }

                logger.info("Creating user ");
                user.setRole(new RoleEntity(1, "PARTY_MAKER"));

                userService.saveUser(user);
                return new ResponseEntity<String>("", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        };
    }
}
