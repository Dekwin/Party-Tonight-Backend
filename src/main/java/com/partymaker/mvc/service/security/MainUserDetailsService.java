package com.partymaker.mvc.service.security;

import com.partymaker.mvc.model.user.User;
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
public class MainUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(MainUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userService.findUserByEmail(s);
        System.out.println(user.getUserRole().getRole() + ", enable = " + user.isEnabled());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> setAuths = new ArrayList<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getRole()));
        setAuths.forEach(System.out::println);
        return setAuths;
    }

}
