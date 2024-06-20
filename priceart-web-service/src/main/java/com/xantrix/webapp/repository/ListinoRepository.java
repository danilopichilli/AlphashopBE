package com.xantrix.webapp.repository;

import com.xantrix.webapp.entity.Listini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListinoRepository extends JpaRepository<Listini, String> {
}
