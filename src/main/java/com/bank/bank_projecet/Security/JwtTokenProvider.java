package com.bank.bank_projecet.Security;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@Log4j2
public class JwtTokenProvider {
    @Value("${security.jwt.secret-key}")
    private  String SECRET_KEY;

    @Value("${security.jwt.expiration-time}")
    private Long JWT_EXPIRATION;

    public String generateToken(Authentication authentication){
        log.error("The error occure here location 1");
       String userName=authentication.getName();
       log.error("The error occure here location 2");
       Date currentDate=new Date();
       log.error("The error occure here location 3");
       Date expireDate=new Date(currentDate.getTime()+JWT_EXPIRATION);
       log.error("The error occure here location 4");

       return Jwts.builder().subject(userName).issuedAt(currentDate).expiration(expireDate).signWith(getSignInKey()).compact();
    }







    private SecretKey  getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserName(String token){

        Claims claims=Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
                try {
                            Jwts.parser().verifyWith(getSignInKey()).build().parse(token);
                            return true;
                    
                } catch (ExpiredJwtException | IllegalArgumentException | SignatureException | MalformedJwtException e) {

                   
                   throw new RuntimeException();
                }
    }
}
