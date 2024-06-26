package com.xantrix.webapp.mapper;

import com.xantrix.webapp.model.dto.UtenteDTO;
import com.xantrix.webapp.model.document.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UtentiMapper {

    UtentiMapper INSTANCE = Mappers.getMapper(UtentiMapper.class);

    Utente toEntity(UtenteDTO utenteDTO);

    UtenteDTO toDto(Utente utente);
}
