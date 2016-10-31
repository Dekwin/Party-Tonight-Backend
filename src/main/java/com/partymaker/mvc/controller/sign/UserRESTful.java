package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.user.User;
import com.partymaker.mvc.service.user.role.UserRoleService;
import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by anton on 10/10/16.
 */
@RestController
@RequestMapping(value = {"/user"})
public class UserRESTful {

    private static Logger logger = Logger.getLogger(UserRESTful.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @GetMapping(value = {"/signin"})
    public Callable<?> signIn(@RequestBody User user, HttpSession session) {
        logger.info("Sign in user: " + user + ", with token = " + session.getId());
        return () -> new ResponseEntity<>(Collections.singletonMap("token", session.getId()), HttpStatus.OK);
    }

    @PostMapping(value = {"/signup"})
    public Callable<?> signUp(@RequestBody User user) {
        logger.info("Signing up user " + user);
        return () -> {
            logger.info("Checking user");
            /*userService.findAllUsers();
            userService.findUserByEmail(user.getEmail());
            userService.deleteUser(1L);
            userService.updateUser(user);*/
            if (!userService.isExist(user.getEmail())) {
                logger.info("Saving user");
                user.setDateUpdate(dateFormat.format(new Date()));
                System.out.println(user.getUserRole().getId());
                user.setUserRole(userRoleService.findUserRoleById(user.getUserRole().getId()));
                user.setEnabled(true);
                logger.info("Saving user " + user);
                userService.saveUser(user);
                logger.info("Saved user: " + user);
                return new ResponseEntity<String>(HttpStatus.OK.toString(), HttpStatus.OK);
            } else {
                logger.info("Conflict with saving user " + user);
                return new ResponseEntity<String>(HttpStatus.CONFLICT.toString(), HttpStatus.CONFLICT);
            }
        };
    }

    @GetMapping(value = {"/test"})
    public Callable<?> test() {
        return () -> new ResponseEntity<String>("Test works", HttpStatus.OK);
    }

}
