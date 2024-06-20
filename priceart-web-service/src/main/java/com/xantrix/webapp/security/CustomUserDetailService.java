package com.xantrix.webapp.security;

import com.xantrix.webapp.entity.model.Utenti;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    private final UserConfig userConfig;

    public CustomUserDetailService(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String errMsg = "";

        if(username == null || username.length() < 2) {
            errMsg = "Nome utente assente o non valido";
            logger.warn(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }

        Utenti utente = this.getHttpValue(username);
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

    private Utenti getHttpValue(String username) {
        URI url = null;

        try {
            String srvUrl = userConfig.getSrvUrl();
            url = new URI(srvUrl + username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(userConfig.getUsername(), userConfig.getPassword()));

        Utenti utente = null;
        try {
            utente = restTemplate.getForObject(url, Utenti.class);
        } catch (Exception e) {
            String errMsg = String.format("Connessione al servizio di autenticazione non riuscita!!");
            logger.warn(errMsg);
        }

        return utente;
    }
}
