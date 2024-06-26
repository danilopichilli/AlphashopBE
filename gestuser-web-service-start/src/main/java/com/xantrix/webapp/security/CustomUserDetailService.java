package com.xantrix.webapp.security;

import com.xantrix.webapp.model.document.Utenti;
import com.xantrix.webapp.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    private final UtenteService utenteService;

    public CustomUserDetailService( UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utenti utente = utenteService.findByUsername(username);
        User.UserBuilder builder = User.withUsername(username);
        builder.disabled((!utente.getAttivo().equals("Si")));
        builder.password(utente.getPassword());
        String[] profili = utente.getRuoli().stream().map(a -> "ROLE_" + a).toArray(String[]::new);
        builder.authorities(profili);
        return builder.build();

    }
}