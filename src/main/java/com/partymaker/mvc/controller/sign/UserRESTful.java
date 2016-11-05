package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import com.partymaker.mvc.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by anton on 10/10/16.
 */
@RestController
public class UserRESTful {

    private static Logger logger = Logger.getLogger(UserRESTful.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @GetMapping(value = {"/signin"})
    public Callable<?> signIn(HttpSession session) {
        logger.info("Sign in user:, with token = " + session.getId());
        return () -> new ResponseEntity<>(Collections.singletonMap("token", session.getId()), HttpStatus.OK);
    }


    @GetMapping(value = {"/user"})
    public Callable<?> getUser() {
        return () -> new ResponseEntity<UserEntity>(new UserEntity(), HttpStatus.OK);
    }

    @GetMapping(value = {"/*/role"})
    public Callable<?> getRole() {
        return () -> new ResponseEntity<RoleEntity>(new RoleEntity(), HttpStatus.OK);
    }
    @GetMapping(value = {"/*/billing"})
    public Callable<?> getBilling() {
        return () -> new ResponseEntity<BillingEntity>(new BillingEntity(), HttpStatus.OK);
    }
}
