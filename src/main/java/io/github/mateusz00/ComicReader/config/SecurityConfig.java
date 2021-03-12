package io.github.mateusz00.ComicReader.config;

import io.github.mateusz00.ComicReader.filter.JWTAuthenticationFilter;
import io.github.mateusz00.ComicReader.filter.JWTAuthorizationFilter;
import io.github.mateusz00.ComicReader.filter.SecurityConstants;
import io.github.mateusz00.ComicReader.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final SecurityConstants securityConstants;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(SecurityConstants securityConstants, UserDetailsServiceImpl userDetailsService) {
        this.securityConstants = securityConstants;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityConstants))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityConstants, userDetailsService))
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized"));
    }

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }
}
