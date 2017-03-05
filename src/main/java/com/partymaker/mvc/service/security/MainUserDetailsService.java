package com.partymaker.mvc.service.security;

import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by anton on 10/10/16.
 */
@Service("userDetailsService")
@Transactional(readOnly = true)
public class MainUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MainUserDetailsService.class);

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("Get user by email " + s);
        UserEntity user = (UserEntity) userService.findUserByEmail(s);
        logger.info("Auth user " + user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnable(), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity user) {
        List<GrantedAuthority> setAuths = new ArrayList<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getUserRole()));
        logger.info("Grants = " + setAuths);
        return setAuths;
    }
}
