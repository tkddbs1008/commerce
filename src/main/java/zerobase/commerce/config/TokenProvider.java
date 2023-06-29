package zerobase.commerce.config;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	
	private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 20;
	
	private String secretKey = "test";
	
	public String generateToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		
		var now = new Date();
		var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now) // 토큰 생성 시간
				.setExpiration(expiredDate) // 토큰 만료 시간
				.signWith(SignatureAlgorithm.HS512, this.secretKey)
				.compact();
	}
	
	public boolean validateToken(String token) {
		if (!StringUtils.hasText(token)) {
			return false;
		}
		
		var claims = this.parsedClaims(token);
		return !claims.getExpiration().before(new Date());
	}
	
	private Claims parsedClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
		} catch(ExpiredJwtException e) {
			return e.getClaims();
		}
		
	}

	public String getUsername(String token) {
		return this.parsedClaims(token).getSubject();
	}
}
