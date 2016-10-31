package com.partymaker.mvc.controller.functional.dancer;

import com.partymaker.mvc.model.user.User;
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

import java.util.concurrent.Callable;

/**
 * Created by anton on 24/10/16.
 */
@RestController
@RequestMapping(value = {"/dancer"})
public class DancerRESTful {

    private static final Logger logger = Logger.getLogger(DancerRESTful.class);

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @PostMapping(value = {"/signup"})
    public Callable<?> signup(@RequestBody User user) {
        return () -> {
            logger.info("Sing up user = " + user);
            try {
                userService.saveUser(user);
            } catch (Exception e) {
                logger.error("Failed to create dancer = " + user + ", due to: " + e);
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
            logger.info("User has been saved");
            return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
        };
    }
}
