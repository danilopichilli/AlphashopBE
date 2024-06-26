package com.xantrix.webapp.model.document;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "utenti")
@Data
public class Utente
{
	public static final String SEQUENCE_NAME = "utenti_sequence";

	@Id
	private String id;

	@Size(min = 1, message = "{Size.Utenti.nome.Validation}")
	@NotNull(message = "{NotNull.Utenti.nome.Validation}")
	private String nome;

	@Size(min = 1, message = "{Size.Utenti.cognome.Validation}")
	@NotNull(message = "{NotNull.Utenti.cognome.Validation}")
	private String cognome;

	@NotNull(message = "{NotNull.Utenti.email.Validation}")
	private String email;

	@Indexed(unique = true)
	@Size(min = 5, max = 80, message = "{Size.Utenti.username.Validation}")
	@NotNull(message = "{NotNull.Articoli.userId.Validation}")
	private String username;

	@NotNull(message = "{NotNull.Utenti.dob.Validation}")
	private LocalDate dob;
	
	@Size(min = 8, max = 80, message = "{Size.Utenti.password.Validation}")
	@NotNull(message = "{NotNull.Utenti.password.Validation}")
	private String password;
	
	private String attivo;
	
	private List<String> ruoli;
	
}
