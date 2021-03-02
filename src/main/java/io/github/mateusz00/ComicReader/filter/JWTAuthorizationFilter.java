package io.github.mateusz00.ComicReader.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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

        if(token != null && token.startsWith(TOKEN_PREFIX)) {
            var authToken = getAuthenticationToken(token);

            if(authToken.isPresent())
                SecurityContextHolder.getContext().setAuthentication(authToken.get());
        }

        chain.doFilter(request, response);
    }

    private Optional<UsernamePasswordAuthenticationToken> getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        if(user != null) {
            // TODO: Add UserDetailsService and add authorities to token
            return Optional.of(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
        }
        else
            return Optional.empty();
    }
}
