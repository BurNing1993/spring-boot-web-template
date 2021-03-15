package com.joey.boot.system.filter;

import com.joey.boot.system.utils.TokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Joey
 */
public class AuthTokenFilter extends BasicAuthenticationFilter {

    private final TokenManager tokenManager;

    public AuthTokenFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean hasToken = tokenManager.hasToken(request);
        if (hasToken) {
            // 验证token
            tokenManager.validateToken(request);
        }
        chain.doFilter(request, response);
    }
}
