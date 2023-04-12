package com.example.wdpai2backend.security;

import com.example.wdpai2backend.entity.RefreshToken;
import com.example.wdpai2backend.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    final private RefreshTokenRepository refreshRepository;

    public JwtService(RefreshTokenRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, SecurityConstants.EXPIRATION_ACCESS_TOKEN);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, SecurityConstants.EXPIRATION_REFRESH_TOKEN);
    }

    public String generateToken(Authentication authentication, long expiration) {
        List<String> authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.KEY)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromJWT(token, Claims::getExpiration);
    }

    public String getUsernameFromJWT(String token) {
        String name = getClaimFromJWT(token, Claims::getSubject);
        System.out.println(name);
        return getClaimFromJWT(token, Claims::getSubject);
    }

    public <T> T getClaimFromJWT(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromJWT(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromJWT(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.KEY))).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token) {
        final Date expiration = getClaimFromJWT(token, Claims::getExpiration);
        System.out.println(expiration);
        System.out.println(new Date(System.currentTimeMillis()));
        return !expiration.before(new Date(System.currentTimeMillis()));
    }

    public void refreshToken(RefreshToken tokenObject) {

        if (!tokenObject.isTokenValid()) {
            refreshRepository.delete(tokenObject);
            new Exception("Refresh token expired");
        }
        tokenObject.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_REFRESH_TOKEN));
        refreshRepository.save(tokenObject);


    }

}
