package jjon.bamyanggang.login.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;


@Component
public class JwtUtil {
	
	private SecretKey secretKey;
	
	
	
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		/* yml 들여쓰기 에러로 못찾는 경우있음 예를들어 spring: 과 같은 줄에있는경우 spring 하위파일로 들어가고
		 * spirng 과 같은줄에 있으면 같은 상위파일이 된다.
	
		*/
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		
	}
	
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public String getCategory(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
	}
	
	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	public String createJwt(String category, String userId, String role, long expiredMs) {
		
		return Jwts.builder()
	            .claim("category", category)
	            .claim("username", userId)
	            .claim("role", role)
	            .issuedAt(new Date(System.currentTimeMillis()))
	            .expiration(new Date(System.currentTimeMillis() + expiredMs))
	            .signWith(secretKey)
	            .compact();
	}
	
	
}
