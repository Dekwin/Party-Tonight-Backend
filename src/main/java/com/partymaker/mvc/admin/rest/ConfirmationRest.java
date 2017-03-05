package com.partymaker.mvc.admin.rest;

import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by anton on 05.03.17.
 */
@RestController
@RequestMapping(value = "/account")
public class ConfirmationRest {

    @Autowired
    UserService userService;

    @GetMapping(value = "/confirm")
    public Callable<ResponseEntity<?>> confirmAccount(@PathVariable("secret") String secret, @PathVariable("account_id") Integer accountId) {
        return () -> {
            try {

                // here need to add the verification secret opt
                // here need to add the verification secret opt
                // here need to add the verification secret opt
                // here need to add the verification secret opt

                userService.userUnLock(accountId);
                return new ResponseEntity<Object>("Successfully confirmed account", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Object>("Failed to confirm account due to " + e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
