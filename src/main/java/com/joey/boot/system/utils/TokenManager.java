package com.joey.boot.system.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Joey
 */
@Component
@Slf4j
public class TokenManager {

    /**
     * Header
     */
    private static final String HEADER_KEY = "Authorization";
    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 密匙
     */
    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    /**
     * 过期时间 6小时
     */
    private static final int EXPIRATION_IN_MS = 6 * 60 * 60 * 1000;


    /**
     * 生成Token
     *
     * @param payload
     * @return
     */
    public String generateToken(String payload) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_IN_MS);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(KEY)
                .compact();
    }

    /**
     * 获取payload
     *
     * @param token
     * @return
     */

    public String getPayloadFromJwt(String token) {
        String payload = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody().getSubject();
        return payload;
    }

    /**
     * 获取payload
     *
     * @param request
     * @return
     */
    public String getPayloadFromRequest(HttpServletRequest request) {
        String token = getToken(request);
        String payload = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody().getSubject();
        return payload;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            //don't trust the JWT!
            log.error("{} is Invalid!{}", token, e);
        }
        return false;
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_KEY);
        if (StringUtils.hasText(token) && StringUtils.containsWhitespace(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        } else {
            throw new JwtException("Invalid Token!");
        }
    }

    /**
     * token是否存在
     *
     * @param request
     * @return token
     */
    public boolean hasToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_KEY);
        return StringUtils.hasText(token) && StringUtils.containsWhitespace(TOKEN_PREFIX);
    }


    /**
     * 验证Token
     *
     * @param request
     * @return
     */
    public boolean validateToken(HttpServletRequest request) {
        try {
            String token = getToken(request);
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            //don't trust the request!
            log.error("Invalid Token!", request, e);
        }
        return false;
    }
}
