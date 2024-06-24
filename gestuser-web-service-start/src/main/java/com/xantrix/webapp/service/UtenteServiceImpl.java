package com.xantrix.webapp.service;

import com.xantrix.webapp.mapper.UtentiMapper;
import com.xantrix.webapp.model.dto.UtentiDTO;
import com.xantrix.webapp.model.document.Utenti;
import com.xantrix.webapp.repository.UtentiRepository;
import com.xantrix.webapp.security.PasswordEncoderConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

    private final UtentiRepository utentiRepository;

    private final PasswordEncoderConfig passwordEncoderConfig;

    private final UtentiMapper utentiMapper;

    public UtenteServiceImpl(UtentiRepository utentiRepository, PasswordEncoderConfig passwordEncoderConfig, UtentiMapper utentiMapper) {
        this.utentiRepository = utentiRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.utentiMapper = utentiMapper;
    }

    @Override
    @Transactional
    public void createSimpleUser(UtentiDTO dto) {
        List<String> userRoles = Collections.singletonList("USER");
        String password = passwordEncoderConfig.passwordEncoder().encode(dto.getPassword());
        dto.setPassword(password);
        dto.setRuoli(userRoles);
        dto.setAttivo("Si");
        Utenti utente = utentiMapper.toEntity(dto);
        utentiRepository.save(utente);
    }

    @Override
    @Transactional
    public void createUser(UtentiDTO dto) {
       String password = passwordEncoderConfig.passwordEncoder().encode(dto.getPassword());
        dto.setPassword(password);
        Utenti utente = utentiMapper.toEntity(dto);
        utentiRepository.save(utente);
    }

    @Override
    public Utenti findById(String id) {
        if(id != null) {
            Optional<Utenti> utente = utentiRepository.findById(id);
            if (utente.isPresent()) {
                return utente.get();
            }
        }
        return null;
    }

    @Override
    public Utenti findByUsername(String username) {
        return utentiRepository.findByUsername(username);
    }

    @Override
    public List<Utenti> findAll() {
        return utentiRepository.findAll();
    }

    @Override
    public void deleteUserById(Utenti utente) {
        utentiRepository.delete(utente);
    }

}