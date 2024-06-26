package com.xantrix.webapp.security;

import java.net.URI;
import java.net.URISyntaxException;

import com.xantrix.webapp.controller.UtenteClient;
import com.xantrix.webapp.entity.model.Utente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	private final UtenteClient utenteClient;

    public CustomUserDetailsService(UtenteClient utenteClient) {
        this.utenteClient = utenteClient;
    }

    @Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException
	{
		String ErrMsg = "";
		
		if (username == null || username.length() < 2)
		{
			ErrMsg = "Nome utente assente o non valido";
			
			logger.warn(ErrMsg);
			
	    	throw new UsernameNotFoundException(ErrMsg); 
		}

		Utente utente = utenteClient.getUtente(username);
		
		if (utente == null)
		{
			ErrMsg = String.format("Utente %s non Trovato!!", username);
			
			logger.warn(ErrMsg);
			
			throw new UsernameNotFoundException(ErrMsg);
		}
		
		UserBuilder builder;
		builder = org.springframework.security.core.userdetails.User.withUsername(utente.getUsername());
		builder.disabled((utente.getAttivo().equals("Si") ? false : true));
		builder.password(utente.getPassword());
		
		String[] profili = utente.getRuoli()
				 .stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		
		builder.authorities(profili);
		
		return builder.build();
		
		
	}
}
	