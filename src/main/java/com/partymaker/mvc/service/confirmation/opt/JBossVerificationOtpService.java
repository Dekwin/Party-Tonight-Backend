package com.partymaker.mvc.service.confirmation.opt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.partymaker.mvc.service.confirmation.opt.model.Secret;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.api.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by anton on 15/12/16. verification
 */
@Service
public class JBossVerificationOtpService implements Otp<String, Secret> {

    private static final Logger logger = LoggerFactory.getLogger(JBossVerificationOtpService.class);

    @Value("${totp.expiration}")
    private String expiration;

    private Totp totp;

    @Override
    public String generate(Secret secret) {
        if (secret == null || secret.getSecret() == null) {
            logger.info("Bad data was received = " + secret);
            throw new RuntimeException("Secret data cannot be null");
        }
        try {
            return new Totp(Base32.encode((secret.getSecret()).getBytes()), new Clock(Integer.parseInt(expiration))).now();
        } catch (Exception e) {
            logger.error("Error during generating opt due to ", e);
            throw new RuntimeException("Secret data cannot be null");
        }
    }

    @Override
    public boolean validate(String opt) throws JsonProcessingException {
        if (opt == null) {
            logger.error("Bad data was received = " + opt);
            throw new RuntimeException("Secret data cannot be null");
        }
        try {
            return totp.verify(String.valueOf(opt));
        } catch (Exception e) {
            logger.error("Error during validating opt due to ", e);
            throw new RuntimeException("Bad opt secret");
        }
    }
}
