package com.xantrix.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xantrix.webapp.model.document.Utenti;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends MongoRepository<Utenti, String>
{
	Optional<Utenti> findById(String id);

	Utenti findByUsername(String username);
}
