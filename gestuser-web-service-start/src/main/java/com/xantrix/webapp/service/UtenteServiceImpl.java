package com.xantrix.webapp.service;

import com.xantrix.webapp.mapper.UtentiMapper;
import com.xantrix.webapp.model.document.Utente;
import com.xantrix.webapp.model.dto.UtenteDTO;
import com.xantrix.webapp.repository.UtentiRepository;
import com.xantrix.webapp.security.PasswordEncoderConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UtenteServiceImpl implements UtenteService {

    private final UtentiRepository utentiRepository;

    private final PasswordEncoderConfig bCrypt;

    private final UtentiMapper utentiMapper;

    public UtenteServiceImpl(UtentiRepository utentiRepository, PasswordEncoderConfig bCrypt, UtentiMapper utentiMapper) {
        this.utentiRepository = utentiRepository;
        this.bCrypt = bCrypt;
        this.utentiMapper = utentiMapper;
    }

    @Override
    @Transactional
    public void createSimpleUser(UtenteDTO dto) {
        List<String> userRoles = Collections.singletonList("USER");
        String password = bCrypt.passwordEncoder().encode(dto.getPassword());
        dto.setPassword(password);
        dto.setRuoli(userRoles);
        dto.setAttivo("Si");
        Utente utente = utentiMapper.toEntity(dto);
        utentiRepository.save(utente);
    }

    @Override
    @Transactional
    public void createUser(UtenteDTO dto) {
        String password = bCrypt.passwordEncoder().encode(dto.getPassword());
        dto.setPassword(password);
        Utente utente = utentiMapper.toEntity(dto);
        utentiRepository.save(utente);
    }

    @Override
    public UtenteDTO findById(String id) {
        if(id != null) {
            Optional<Utente> utente = utentiRepository.findById(id);
            if (utente.isPresent()) {
                UtenteDTO dto = utentiMapper.toDto(utente.get());
                return dto;
            }
        }
        return null;
    }

    @Override
    public UtenteDTO findByUsername(String username) {
        Utente entity = utentiRepository.findByUsername(username);
        if (entity != null) {
            return utentiMapper.toDto(entity);
        }
        return null;
    }

    @Override
    public List<Utente> findAll() {
        return utentiRepository.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        utentiRepository.deleteById(id);
    }

}