package io.github.mateusz00.ComicReader.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static io.github.mateusz00.ComicReader.filter.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null && token.startsWith(TOKEN_PREFIX))
            SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(token));

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        if(user != null)
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        else
            return null;
    }
}
