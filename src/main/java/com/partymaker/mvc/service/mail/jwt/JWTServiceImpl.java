package com.partymaker.mvc.service.mail.jwt;

import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.user.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by igorkasyanenko on 07.04.17.
 */
@Service
public class JWTServiceImpl implements JWTService {

    public static final String TOKEN_PURPOSE_VERIFY_USER = "verifyAccount";
    public static final String TOKEN_PURPOSE_RESET_PASSWORD = "resetPassword";
    private final String SECRET = "key123";

    @Autowired
    UserService userService;

//    private String tokenPrefix = "Bearer";
//    private String headerString = "Authorization";


    @Override
    public String getToken(UserEntity forUser, String purpose) {
        Map<String, Object> tokenData = new HashMap<>();

        tokenData.put("userID", forUser.getId_user());

        if (purpose != null)
            tokenData.put("purpose", purpose);
        if(TOKEN_PURPOSE_RESET_PASSWORD.equals(purpose)) {
            tokenData.put("salt", forUser.getPassword());
            tokenData.put("email", forUser.getEmail());
        }

        tokenData.put("TOKEN_CREATE_DATE", new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        tokenData.put("TOKEN_EXPIRATION_DATE", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);

        String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, SECRET).compact();
        return token;

    }

    @Override
    public UserEntity getUser(String forToken, String purpose) throws AuthenticationException {

        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(SECRET).parse(forToken).getBody();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("TOKEN_EXPIRATION_DATE", Long.class) == null)
            throw new AuthenticationServiceException("Invalid token");
        Date expiredDate = new Date(claims.get("TOKEN_EXPIRATION_DATE", Long.class));
        if (expiredDate.after(new Date()) && purpose != null ? purpose.equals(claims.get("purpose", String.class)) : true) {

            UserEntity foundUser = (UserEntity) userService.findUserById(claims.get("userID", Integer.class));

           if(TOKEN_PURPOSE_RESET_PASSWORD.equals(claims.get("purpose",String.class)))
            if (foundUser.getPassword().equals(claims.get("salt",String.class))
                    &&foundUser.getEmail().equals(claims.get("email",String.class))){
                return  foundUser;
            }else{
                throw new AuthenticationServiceException("Token was used");
            }

            return foundUser;
        } else
            throw new AuthenticationServiceException("Token expired date error");
    }


}
