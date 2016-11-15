package com.partymaker.mvc.controller.sign;

import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.event;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import com.partymaker.mvc.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        return () -> {
            /*try {
                event event = new event();
                Path file = Paths.get("/home/anton/deploy/-1895623107.jpg");
                byte[] content;
                content = Files.readAllBytes(file);
                List<MultipartFile> images = new ArrayList<>();

                images.add((new MockMultipartFile("new", content)));
                images.add((new MockMultipartFile("new", content)));
                images.add((new MockMultipartFile("new", content)));
                return new ResponseEntity<List>(images, HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;*/
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
}
