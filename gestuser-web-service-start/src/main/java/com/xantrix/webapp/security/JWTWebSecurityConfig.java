package com.xantrix.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenAuthorizationOncePerRequestFilter jwtTokenAuthorizationOncePerRequestFilter;

    private final UserDetailsService userDetailsService;

    private final JwtUnAuthorizedResponeAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

    private final PasswordEncoderConfig passwordEncoderConfig;

    @Value("${jwt.uri}")
    private String authenticationPath;

    public JWTWebSecurityConfig(JwtTokenAuthorizationOncePerRequestFilter jwtTokenAuthorizationOncePerRequestFilter, UserDetailsService userDetailsService, JwtUnAuthorizedResponeAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint, PasswordEncoderConfig passwordEncoderConfig) {
        this.jwtTokenAuthorizationOncePerRequestFilter = jwtTokenAuthorizationOncePerRequestFilter;
        this.userDetailsService = userDetailsService;
        this.jwtUnAuthorizedResponseAuthenticationEntryPoint = jwtUnAuthorizedResponseAuthenticationEntryPoint;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    private static final String[] USER_MATCHER = { "/api/utenti/cerca/username/**"};
    private static final String[] ADMIN_MATCHER = { "/api/utenti/inserisci/**", "/api/utenti/elimina/**", "/api/utenti/cerca/tutti" };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(USER_MATCHER).hasRole("USER")
                .antMatchers(ADMIN_MATCHER).hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtTokenAuthorizationOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().sameOrigin().cacheControl();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity.ignoring().antMatchers(HttpMethod.POST, authenticationPath)
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .and().ignoring()
                .antMatchers(HttpMethod.GET, "/");
    }
}
