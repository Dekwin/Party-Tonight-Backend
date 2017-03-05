package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.admin.confirmation.ConfirmationService;
import com.partymaker.mvc.service.user.UserService;
import com.partymaker.mvc.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by anton on 10/10/16.
 */
@RestController
public class UserSignIn {

    private static final Logger logger = LoggerFactory.getLogger(UserSignIn.class);


    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    ConfirmationService confirmationService;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @PostMapping(value = {"/user/confirm"})
    public Callable<ResponseEntity<?>> confirmUser(String opt) {
        logger.info("Confirmation by opt = " + opt);
        return () -> {
            if (opt == null || opt.equals(""))
                return new ResponseEntity<Object>("Bad data.", HttpStatus.BAD_REQUEST);
            try {
                confirmationService.confirm(opt);
                return new ResponseEntity<Object>("Successful", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Object>("Bad data.", HttpStatus.BAD_REQUEST);
            }
        };
    }

    @GetMapping(value = {"/signin"})
    public Callable<?> tokens(HttpSession session) {
        logger.info("Sign in user: with token = " + session.getId());
        logger.info("User details : " + getPrincipal());
        return () -> new ResponseEntity<>(Collections.singletonMap("token", session.getId()), HttpStatus.OK);
    }


    @GetMapping(value = {"/user"})
    public Callable<?> getUser() {
        return () -> {
            return new ResponseEntity<UserEntity>(new UserEntity(), HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/*/role"})
    public Callable<?> getRole() {
        return () -> new ResponseEntity<RoleEntity>(new RoleEntity(), HttpStatus.OK);
    }

    @GetMapping(value = {"/*/billing"})
    public Callable<?> getBilling() {
        return () -> new ResponseEntity<BillingEntity>(new BillingEntity(), HttpStatus.OK);
    }

    @GetMapping(value = {"/event"})
    public Callable<ResponseEntity<?>> getevent() {
        return () -> {

            List<event> list = new ArrayList<event>();
            list.add(new event());
            list.add(new event());
            list.add(new event());
            return new ResponseEntity<Object>(list, HttpStatus.OK);
        };
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
