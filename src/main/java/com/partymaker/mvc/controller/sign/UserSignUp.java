package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.enums.Roles;
import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.admin.AdminService;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.Callable;

/**
 * User sign up controller
 * Performs a registration of both to kinds of roles:
 *
 * @maker
 * @dancer
 */

@RestController
public class UserSignUp {

    private static final Logger logger = Logger.getLogger(UserSignUp.class);

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @PostMapping(value = {"/maker/signup"})
    public Callable<ResponseEntity<?>> signUpMaker(@RequestBody UserEntity user, HttpServletRequest request) {
        return () -> {
            logger.info("Sign up maker = " + user);
            try {
                logger.info("Checking user data on existing ");

                userService.isExistUserRequiredFields(user);

                logger.info("Creating user ");
                user.setRole(new RoleEntity(2, Roles.ROLE_PARTY_MAKER.toString()));
                user.setBillingEmail(user.getBillingEntity().getBilling_email());
                user.setVerified(false);
                user.setEnable(true);
                userService.saveUser(user);


                adminService.sendVerificationMail(user.getEmail(),request.getServerName());

                return new ResponseEntity<String>("", HttpStatus.CREATED);
            }catch (ConnectException e){
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }catch(IOException e){
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }catch(MessagingException e){
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            catch(RuntimeException e){
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            catch (Exception e) {
                logger.info("Failed to create maker = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        };
    }

    @PostMapping(value = {"/dancer/signup"})
    public Callable<ResponseEntity<?>> signUpDancer(@RequestBody UserEntity user, HttpServletRequest request) {
        return () -> {
            logger.info("Sing up dancer = " + user);
            try {

                userService.isExistUserRequiredFields(user);

                logger.info("Saving user ");
                // hard code
                user.setRole(new RoleEntity(3, Roles.ROLE_STREET_DANCER.toString()));
                user.setVerified(false);
                user.setEnable(true);
                userService.saveUser(user);

                adminService.sendVerificationMail(user.getEmail(),request.getServerName());

                logger.info("User has been saved");

                return new ResponseEntity<String>("Successful registration!", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create dancer = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        };
    }


    @PostMapping(value = {"/admin/signup"})
    public Callable<ResponseEntity<?>> signUpAdmin(@RequestBody UserEntity user1, HttpServletRequest request) {
        return () -> {

            //mock
            UserEntity user = new UserEntity();
            user.setUserName("test");
            user.setPassword("a");
            user.setVerified(false);
            String email = "dekstersniper@gmail.com";
            user.setEmail(email);
            user.setBillingEmail(email);
            BillingEntity billingEntity = new BillingEntity();
            billingEntity.setBillingEmail(email);
            user.setBillingEntity(billingEntity);



            logger.info("Sign up admin = " + user);
            try {
                logger.info("Checking user data on existing ");

                userService.isExistUserRequiredFields(user);

                logger.info("Creating user ");
                user.setRole(new RoleEntity(1, "ROLE_ADMIN"));
                user.setBillingEmail(user.getBillingEntity().getBilling_email());


                userService.saveUser(user);

                adminService.sendVerificationMail(user.getEmail(),request.getServerName());

                return new ResponseEntity<String>("", HttpStatus.CREATED);
            } catch (Exception e) {
                logger.info("Failed to create admin = " + user + ", due to: ", e);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        };
    }

    @GetMapping(value = {"/accounts/reset"})
    public Callable<ResponseEntity<?>> verifyTokenToResetPassword(@RequestParam("token") String token) {
        return () -> {
            try {
                adminService.verifyTokenToResetPassword(token);
                return new ResponseEntity<Object>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Object>(e,HttpStatus.CONFLICT);
            }
        };
    }

    @PostMapping(value = {"/accounts/reset"})
    public Callable<ResponseEntity<?>> resetPassword(@RequestParam("email") String email,  HttpServletRequest request) {
        return () -> {
            try {

                adminService.sendTokenToResetPassword(email, request.getServerName());
                return new ResponseEntity<Object>(HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<Object>(e,HttpStatus.CONFLICT);
            }
        };
    }


    @GetMapping(value = {"/accounts/verify"})
    public Callable<ResponseEntity<?>> verifyAccount(@RequestParam("token") String token) {
        return () -> {
            try {
                adminService.verifyUserByToken(token);
                return new ResponseEntity<Object>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<Object>(HttpStatus.CONFLICT);
            }
        };
    }




}
