package com.xantrix.webapp.service;

import com.xantrix.webapp.entity.Listini;
import com.xantrix.webapp.repository.ListinoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ListinoServiceImpl implements ListinoService{

    private final ListinoRepository listinoRepository;


    public ListinoServiceImpl(ListinoRepository listinoRepository) {
        this.listinoRepository = listinoRepository;
    }

    @Override
    @Transactional
    public void saveListino(Listini listino) {
        listinoRepository.save(listino);
    }

    @Override
    public Optional<Listini> findListinoById(String id) {
        return listinoRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteListino(Listini listino) {
        listinoRepository.delete(listino);
    }
}
