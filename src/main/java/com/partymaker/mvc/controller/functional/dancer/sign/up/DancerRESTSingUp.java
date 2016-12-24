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
import java.util.UUID;
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
                try {
                    if (userService.isExist(user.getEmail())) {
                        return new ResponseEntity<Object>("Goer with current email has already exist", HttpStatus.FORBIDDEN);
                    }
                    if (userService.isExistByName(user.getUserName())) {
                        return new ResponseEntity<Object>("Goer with current name has already exist", HttpStatus.FORBIDDEN);
                    }
                    /**
                     *
                     * it will be uncommented with PayPal needs
                     * */
                   /* if (billingService.isExist(user.getBilling())) {
                        return new ResponseEntity<Object>("Goer with current billing info has already exist", HttpStatus.FORBIDDEN);
                    }*/
                } catch (Exception e) {
                    logger.info("Checking failed" + e);
                    return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
                }

                date = new Date();
                user.setCreatedDate(dateFormat.format(date));
                // hard code
                user.setRole(new RoleEntity(1, "STREET_DANCER"));

                /* a little  hard code */
                BillingEntity billing = new BillingEntity("dancer" + UUID.randomUUID().toString());
                billingService.saveBilling(billing);

                // sent enable without confirmation
                user.setEnable(true);

                user.setBilling(billingService.findByCard(billing.getCard_number()));

                userService.saveUser(user);
                logger.info("User has been saved");
                return new ResponseEntity<String>("Successful registration!", HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Failed to create dancer = " + user + ", due to: " + e);
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
        };
    }
}
