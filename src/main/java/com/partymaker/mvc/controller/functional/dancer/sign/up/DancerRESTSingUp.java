package com.partymaker.mvc.controller.functional.dancer.sign.up;

import com.partymaker.mvc.model.whole.BillingEntity;
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
 * Created by anton on 24/10/16.
 */
@RestController
@RequestMapping(value = {"/dancer"})
public class DancerRESTSingUp {

    private static final Logger logger = Logger.getLogger(DancerRESTSingUp.class);

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date;

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @Autowired
    @Qualifier("BillingService")
    BillingService billingService;

    @PostMapping(value = {"/signup"})
    public Callable<ResponseEntity<?>> signup(@RequestBody UserEntity user) {
        return () -> {
            logger.info("Sing up dancer = " + user);
            try {

                if (user == null) {
                    return new ResponseEntity<Object>("User cannot be null", HttpStatus.BAD_REQUEST);
                } else if (user.getPassword() == null || user.getEmail() == null) {
                    return new ResponseEntity<Object>("User fields cannot be null.", HttpStatus.BAD_REQUEST);
                }

                /* a little  hard code it will be replaced */
                BillingEntity billing = new BillingEntity("dancer" + System.currentTimeMillis());
                user.setBilling(billing);

                ResponseEntity responseEntity = userService.isExistUserRequiredFields(user);
                if (responseEntity != null) {
                    logger.info("Bad user data");
                    return responseEntity;
                }
                logger.info("Saving user ");
                // hard code
                user.setRole(new RoleEntity(2, "STREET_DANCER"));

                userService.saveUser(user);

                logger.info("User has been saved");

                return new ResponseEntity<String>("Successful registration!", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create dancer = " + user + ", due to: ", e);
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
        };
    }
}
