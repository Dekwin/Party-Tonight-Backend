package com.partymaker.mvc.admin.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.partymaker.mvc.admin.confirmation.mail.AdminService;
import com.partymaker.mvc.model.admin.Admin;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

/**
 * Created by anton on 12.02.17.
 */
@RestController
@RequestMapping(value = {"/admin"})
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    AdminService adminService;


    @GetMapping(value = {"/event/all"})
    public Callable<ResponseEntity<?>> getAllEvents() {
        return () -> {
            logger.info("Get all events by " + getPrincipal());

            return new ResponseEntity<Object>(eventService.findAll(), HttpStatus.OK);
        };
    }

    @GetMapping(value = {"/user/all"})
    public Callable<ResponseEntity<?>> getAllUser() {
        return () -> {
            logger.info("Get all users by " + getPrincipal());
            return new ResponseEntity<Object>(userService.findAllUsers(), HttpStatus.OK);
        };
    }

    @PostMapping(value = {"/user/hold"})
    public Callable<ResponseEntity<?>> holdUser(@RequestHeader("user_id") String user_id) {
        return () -> {
            logger.info("Hold user with id " + user_id);

            if (user_id == null || user_id.equals(""))
                return new ResponseEntity<Object>("User data cannot be null", HttpStatus.BAD_REQUEST);

            try {

                userService.userLock(Integer.parseInt(user_id));

                return new ResponseEntity<Object>("User is locked.", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error locking user ", e);
                return new ResponseEntity<Object>("Something was wrong, pleas try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/user/delete"})
    public Callable<ResponseEntity<?>> deleteUser(@RequestHeader("user_id") String user_id) {
        return () -> {
            logger.info("Delete user with id ");

            if (user_id == null || user_id.equals(""))
                return new ResponseEntity<Object>("User data cannot be null", HttpStatus.BAD_REQUEST);

            try {

                userService.deleteUser(Integer.parseInt(user_id));

                return new ResponseEntity<Object>("Deleted", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error deleting user due to ", e);
                return new ResponseEntity<Object>("Something was wrong pleas try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/user/verify"})
    public Callable<ResponseEntity<?>> verifyUser(@RequestHeader("user_id") String user_id) {
        return () -> {
            return new ResponseEntity<Object>(HttpStatus.OK);
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

    @PostMapping(value = "/credentals/change")
    public Callable<ResponseEntity<?>> changeAdminCredentials(@RequestBody JsonNode requestBody) {
        return () -> {
            logger.info("Changing admin credentials by " + getPrincipal());
            try {

                UserEntity entity = (UserEntity) userService.findUserByEmail(getPrincipal());
                entity.setEmail(requestBody.get("new_login").textValue());
                entity.setEmail(requestBody.get("new_password").textValue());

                logger.info("Updated user " + entity);
                userService.updateUser(entity);

                return new ResponseEntity<Object>(HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during change admin credentials due to ", e);
                return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = "/create")
    public Callable<ResponseEntity<?>> createAdmin(@RequestBody Admin admin) {
        return () -> {
            logger.info("Creating admin  data " + admin);
            try {
                adminService.saveAdminEntity(admin);
                return new ResponseEntity<Object>(HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error creating admin due to ", e);
                return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

}
