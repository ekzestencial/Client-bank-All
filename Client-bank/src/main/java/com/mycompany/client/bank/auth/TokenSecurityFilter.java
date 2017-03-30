/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.auth;

/**
 *
 * @author alex
 */
	
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 *
 * @author al
 */
public class TokenSecurityFilter extends OncePerRequestFilter{
 public final String AUTH_HTTP_HEADER = "X-Authorization";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenProvider tokenPtovider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //DEBUG: print all HTTP headers
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            logger.info("Name: " + headerName + " Value: " + request.getHeader(headerName));
        }
        String authToken = request.getHeader(AUTH_HTTP_HEADER);
        if (authToken == null) {
            authToken = request.getHeader(AUTH_HTTP_HEADER.toLowerCase());
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthUser u = tokenPtovider.get(authToken);
            if (u != null) {
                boolean isTokenValid = tokenPtovider.validateToken(authToken, u);
                if (isTokenValid) {
                    logger.debug("Token found, it belongs to: " + u.getUsername());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + u.getUsername() + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                logger.info("authentication failed, may be token is expired");
            }
        }
        //we must call all filters in the chain
        filterChain.doFilter(request, response);
    }
}