package speechToText.api.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
	  
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
     
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("Safta")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
}
    
    
    
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
        	System.out.println("Token is null, empty or only whitespace" +ex.getMessage());
        } catch (MalformedJwtException ex) {
        	System.out.println ("JWT is invalid " + ex);
        } catch (UnsupportedJwtException ex) {
        	System.out.println("JWT is not supported"+ex);
        } catch (SignatureException ex) {
        	System.out.println("Signature validation failed");
        }
         
        return false;
    }
     
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
