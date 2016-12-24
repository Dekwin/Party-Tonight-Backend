package com.partymaker.mvc.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)// to enable @Secured,@PreAuthorize
public class Security extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsService userDetailsService;

    /* */
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/maker/signup", "/dancer/signup", "/user", "/event","/maker/event/photo").permitAll()
                .antMatchers("/signin").access("hasRole('ROLE_STREET_DANCER') or hasRole('ROLE_PARTY_MAKER')")
                .antMatchers("/maker/event/**").access("hasRole('ROLE_PARTY_MAKER')")
                .antMatchers("/dancer/event/**").access("hasRole('ROLE_STREET_DANCER')")
                .anyRequest().authenticated()

                /*.and().formLogin().defaultSuccessUrl("/token")*/
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/user/test")
                .and().requestCache()
                .requestCache(new NullRequestCache())
                .and()
                .httpBasic();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
