package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.user.User;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.concurrent.Callable;


@RestController
@RequestMapping(value = {"/"})
public class SignRESTUser {

    /*logger */
    private static Logger logger = Logger.getLogger(SignRESTUser.class);

    @Autowired
    UserService userService;



    @GetMapping(value = {"token"})
    @ResponseBody
    public Callable<?> retrive(HttpSession session) {
        return () -> {
            logger.info("Inside giving token method");
            return Collections.singletonMap("token", session.getId());
        };
    }

    @GetMapping(value = "greeting")
    @CrossOrigin(origins = "*",
            allowedHeaders = {"x-auth-token", "x-requested-with"}
            , maxAge = 3600)
    public Callable<?> greeting() {
        return () -> new ResponseEntity<String>("This is the greeting", HttpStatus.OK);
    }

    @GetMapping(value = {"signup"})
    public Callable<?> signIn(@RequestBody User user) {
        logger.info("Received " + user);
        return () -> new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @CrossOrigin(origins = "df",
            allowedHeaders = {"x-auth-token", "x-requested-with"}
            , maxAge = 3600)
    @RequestMapping("/api/users")
    public String authorized() {
        return "Hello Secured World";
    }

}
