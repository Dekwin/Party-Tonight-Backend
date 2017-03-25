package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * User sign up controller
 * Performs a registration of both to kinds of roles:
 *
 * @maker
 * @dancer
 */

@RestController
public class UserSignUp {

    private static final Logger logger = Logger.getLogger(UserSignUp.class);

    @Autowired
    UserService userService;


    @PostMapping(value = {"/maker/signup"})
    public Callable<ResponseEntity<?>> signUpMaker(@RequestBody UserEntity user) {
        return () -> {
            logger.info("Sign up maker = " + user);
            try {
                logger.info("Checking user data on existing ");

                userService.isExistUserRequiredFields(user);

                logger.info("Creating user ");
                user.setRole(new RoleEntity(1, "PARTY_MAKER"));
                user.setBillingEmail(user.getBillingEntity().getBilling_email());

                userService.saveUser(user);

                return new ResponseEntity<String>("", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        };
    }

    @PostMapping(value = {"/dancer/signup"})
    public Callable<ResponseEntity<?>> signUpDancer(@RequestBody UserEntity user) {
        return () -> {
            logger.info("Sing up dancer = " + user);
            try {

                userService.isExistUserRequiredFields(user);

                logger.info("Saving user ");
                // hard code
                user.setRole(new RoleEntity(2, "STREET_DANCER"));

                userService.saveUser(user);

                logger.info("User has been saved");

                return new ResponseEntity<String>("Successful registration!", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create dancer = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        };
    }
}
