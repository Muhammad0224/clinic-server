package uz.boss.appclinicserver.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Murtazayev Muhammad
 * @since 25.12.2021
 */
@Component
public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String key;

    @Value("${jwt.expire-time-access}")
    private long expireTimeAccess;

    @Value("${jwt.expire-time-refresh}")
    private long expireTimeRefresh;


    public String generateToken(String username, boolean forAccess) {
        Date expireDate = new Date(System.currentTimeMillis() + (forAccess ? expireTimeAccess : expireTimeRefresh));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .claim("expireIn", expireDate.getTime())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
           return null;
        }
    }

    public long getExpireTime(String token){
        try {
            return (long) Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("expireIn");
        }catch (Exception e) {
            return 0;
        }
    }

    public void validateToken(String token){
        Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
    }
}
