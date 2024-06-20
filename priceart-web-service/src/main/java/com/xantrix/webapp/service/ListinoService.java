package com.xantrix.webapp.service;


import com.xantrix.webapp.entity.Listini;

import java.util.Optional;

public interface ListinoService {
    void saveListino(Listini listino);

    Optional<Listini> findListinoById(String id);

    void deleteListino(Listini listino);

}
