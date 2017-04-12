package com.partymaker.mvc.controller.adminpanel;

import com.fasterxml.jackson.databind.JsonNode;
import com.partymaker.mvc.model.enums.Roles;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.admin.AdminService;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.partymaker.mvc.controller.functional.dancer.event.EventDancer.OWNER_FEE;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
@RestController
@RequestMapping(value = {"/admin"})
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    AdminService adminService;


//    @GetMapping(value = {"/events"})
//    public Callable<ResponseEntity<?>> getAllEvents() {
//        return () -> {
//            logger.info("Get all events by " + getPrincipal());
//
//            return new ResponseEntity<Object>(eventService.findAll(), HttpStatus.OK);
//        };
//    }

    @GetMapping(value = {"/events"})
    public Callable<ResponseEntity<?>> getAllEvents(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return () -> {

            logger.info("Get all events by " + getPrincipal());
            return new ResponseEntity<Object>(eventService.findAll(offset, limit, null), HttpStatus.OK);
        };
    }


    @GetMapping(value = {"/users"})
    public Callable<ResponseEntity<?>> getAllUsers(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit, @RequestParam("role") String role) {
        return () -> {
            logger.info("Get all users by " + getPrincipal());
            return new ResponseEntity<Object>(userService.findByRole(offset, limit, role, Order.asc("userName")), HttpStatus.OK);
        };
    }

    @PostMapping(value = {"/users/lock"})
    public Callable<ResponseEntity<?>> holdUser(@RequestBody Map<String, String> json) {
        return () -> {
            String userId = json.get("id");
            logger.info("Lock user with id " + userId);

            if (userId == null || userId.equals(""))
                return new ResponseEntity<Object>("User data cannot be null", HttpStatus.BAD_REQUEST);

            try {

                userService.disableUserById(Integer.parseInt(userId));

                return new ResponseEntity<Object>("User is locked.", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error locking user ", e);
                return new ResponseEntity<Object>("Something was wrong, please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/users/unlock"})
    public Callable<ResponseEntity<?>> unholdUser(@RequestBody Map<String, String> json) {
        return () -> {
            String userId = json.get("id");
            logger.info("Unlock user with id " + userId);

            if (userId == null || userId.equals(""))
                return new ResponseEntity<Object>("User data cannot be null", HttpStatus.BAD_REQUEST);

            try {

                userService.enableUserById(Integer.parseInt(userId));

                return new ResponseEntity<Object>("User is unlocked.", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error unlocking user ", e);
                return new ResponseEntity<Object>("Something was wrong, please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = {"/users/delete"})
    public Callable<ResponseEntity<?>> deleteUser(@RequestBody Map<String, String> json) {
        return () -> {
            String userId = json.get("id");
            logger.info("Delete user with id ");

            if (userId == null || userId.equals(""))
                return new ResponseEntity<Object>("User data cannot be null", HttpStatus.BAD_REQUEST);

            try {

                userService.deleteUser(Integer.parseInt(userId));

                return new ResponseEntity<Object>("Deleted", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error deleting user due to ", e);
                return new ResponseEntity<Object>("Something was wrong, please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }


    @PostMapping(value = {"/users/verify"})
    public Callable<ResponseEntity<?>> verifyUser(@RequestBody Map<String, String> json) {
        return () -> {


            userService.setUserVerifiedById(Integer.parseInt(json.get("id")));
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

    @PostMapping(value = "/credentials")
    public Callable<ResponseEntity<?>> changeAdminCredentials(@RequestBody JsonNode requestBody, HttpServletRequest request) {
        return () -> {
            logger.info("Changing admin credentials by " + getPrincipal());
            try {

                String newEmail = requestBody.get("newEmail").textValue();
                String newPassword = requestBody.get("newPassword").textValue();

                UserEntity entity = null;
                if (newEmail != null && !newEmail.equals("")) {
                     entity = userService.updateEmail(getPrincipal(), newEmail);
                    logger.info("Updated user " + entity);
                }
                if (newPassword != null && !newPassword.equals("")) {
                    adminService.sendTokenToResetPassword(entity != null ? entity.getEmail() : getPrincipal(), request.getServerName());
                }

              //  userService.updateUser(entity);

                return new ResponseEntity<Object>(HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error during change admin credentials due to ", e);
                return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PostMapping(value = "/create")
    public Callable<ResponseEntity<?>> createAdmin(@RequestBody UserEntity admin) {
        return () -> {
            logger.info("Creating admin data " + admin);
            try {
                userService.validationUser(admin);
                adminService.saveUserAsAdmin(admin);
                return new ResponseEntity<Object>(HttpStatus.OK);
            } catch (Exception e) {
                logger.info("Error creating admin due to ", e);
                return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }


    @GetMapping(value = {"/commercial/fee"})
    public Callable<ResponseEntity<?>> getCommercialFee() {
        return () -> {
           // logger.info("Get fee by " + getPrincipal());

            Double feePercent = getFee();

            return new ResponseEntity<Object>(feePercent, HttpStatus.OK);
        };
    }

    @PostMapping(value = {"/commercial/fee"})
    public Callable<ResponseEntity<?>> setCommercialFee(@RequestBody JsonNode requestBody) {
        return () -> {
            double feePercent = requestBody.get("amount").asDouble();

            setFee(feePercent);

            return new ResponseEntity<Object>(HttpStatus.OK);
        };
    }

    //fixme must be in paypal service
    private void setFee(double fee){
        if(fee >= 0 && fee <= 100){
            double resultFee = fee/100;
            logger.info("New fee was set: "+resultFee);
            OWNER_FEE =  resultFee;
        }
    }

    private double getFee(){
      return OWNER_FEE*100;
    }


}
