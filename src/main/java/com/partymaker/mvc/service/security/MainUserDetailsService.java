package com.partymaker.mvc.service.security;

import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Created by anton on 10/10/16.
 */
@Service("userDetailsService")
@Transactional(readOnly = true)
public class MainUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(MainUserDetailsService.class);

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = (UserEntity) userService.findUserByEmail(s);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity user) {
        List<GrantedAuthority> setAuths = new ArrayList<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getUserRole()));
        setAuths.forEach(System.out::println);
        return setAuths;
    }

}
