package com.partymaker.mvc.controller.functional.maker;

import com.partymaker.mvc.model.user.User;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * partymaker controller
 * Created by Mostipan Anton
 */

@RestController
@RequestMapping(value = {"/maker"})
public class PartyMakerRESTful {

    private static final Logger logger = Logger.getLogger(PartyMakerRESTful.class);

    @Autowired
    UserService userService;

    @PostMapping(value = {"/signup"})
    public Callable<?> signUp(@RequestBody User user) {
        return () -> {
            logger.info("Sign up maker = " + user);
            try {
                userService.saveUser(user);
            } catch (Exception e) {
                logger.error("Failed to create maker = " + user + ", due to: " + e);
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
            logger.info("Created maker = " + user);
            return new ResponseEntity<String>("", HttpStatus.CREATED);
        };
    }
}
