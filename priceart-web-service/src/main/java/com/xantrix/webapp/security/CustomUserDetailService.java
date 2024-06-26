package com.xantrix.webapp.security;

import com.xantrix.webapp.controller.UtenteClient;
import com.xantrix.webapp.entity.model.Utente;
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

    private final UtenteClient utenteClient;

    public CustomUserDetailService(UtenteClient utenteClient) {
        this.utenteClient = utenteClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String errMsg = "";

        if(username == null || username.length() < 2) {
            errMsg = "Nome utente assente o non valido";
            logger.warn(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }

        Utente utente = utenteClient.getUtente(username);
        if (utente == null) {
            errMsg = String.format("Utente %s non trovato!!", username);
            logger.warn(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }

        User.UserBuilder builder = null;
        builder = org.springframework.security.core.userdetails.User.withUsername(utente.getUsername());
        builder.disabled((utente.getAttivo().equals("Si") ? false : true));
        builder.password(utente.getPassword());
        String[] profili = utente.getRuoli().stream().map(a -> "ROLE_" + a).toArray(String[]::new);
        builder.authorities(profili);

        return builder.build();
    }
}
