package com.xantrix.webapp.repository;

import com.xantrix.webapp.model.document.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends MongoRepository<Utente, String>
{
	Optional<Utente> findById(String id);

	Utente findByUsername(String username);
}
