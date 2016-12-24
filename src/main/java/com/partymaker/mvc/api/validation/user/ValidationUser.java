package com.partymaker.mvc.api.validation.user;

import com.partymaker.mvc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by anton on 28/10/16.
 */
public class ValidationUser implements Validator {
    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> object) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
