package com.xantrix.webapp.mapper;

import com.xantrix.webapp.model.dto.UtentiDTO;
import com.xantrix.webapp.model.document.Utenti;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtentiMapper {

    Utenti toEntity(UtentiDTO utentiDTO);

    UtentiDTO toDto(Utenti utenti);
}
