package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.service.user.UserService;
import com.partymaker.mvc.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by anton on 10/10/16.
 */


@RestController
public class UserSignIn {

    private static Logger logger = Logger.getLogger(UserSignIn.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");




    @GetMapping(value = {"/signin"})
    public Callable<?> tokens(HttpSession session) {
        logger.info("Sign in user: with token = " + session.getId());
        logger.info("User details : " + getPrincipal());
        UserEntity userEntity = (UserEntity) userService.findUserByEmail(getPrincipal());
        Map<String, String> m = new HashMap<String , String >();
        m.put("token", session.getId());
        m.put("role",userEntity.getRole().getUserRole());
        //Collections.singletonMap("token", session.getId())
        return () -> new ResponseEntity<>(m, HttpStatus.OK);
    }


    @GetMapping(value = {"/user"})
    public Callable<?> getUser() {
        return () -> {
            return new ResponseEntity<UserEntity>(new UserEntity(),HttpStatus.OK);
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
