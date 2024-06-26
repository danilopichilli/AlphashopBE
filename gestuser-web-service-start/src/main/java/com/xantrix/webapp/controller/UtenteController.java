package com.xantrix.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.model.document.Utente;
import com.xantrix.webapp.model.dto.UtenteDTO;
import com.xantrix.webapp.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/utenti")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController {

    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    private final ResourceBundleMessageSource errMessage;

    private final UtenteService utenteService;

    public UtenteController(ResourceBundleMessageSource errMessage, UtenteService utenteService) {
        this.errMessage = errMessage;
        this.utenteService = utenteService;
    }

    @PostMapping(value = "/registrazione", produces = "application/json")
    public ResponseEntity<?> registerSimpleUser(@Validated @RequestBody UtenteDTO dto, BindingResult bindingResult) throws BindingException, DuplicateException {

        if (bindingResult.hasErrors()) {
            String errMsg = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            logger.warn(errMsg);

            throw new BindingException(errMsg);
        }

        UtenteDTO checkUtente = utenteService.findByUsername(dto.getUsername());
        if (checkUtente != null) {
            String errMsg = String.format("Utente con username %s già presente in anagrafica", dto.getUsername());
            logger.warn(errMsg);

            throw new DuplicateException(errMsg);
        }

        utenteService.createSimpleUser(dto);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Inserimento Utente " + dto.getUsername() + " Eseguito Con Successo");

        return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
    }

    @PostMapping(value = "/inserisci", produces = "application/json")
    public ResponseEntity<?> createUser(@Validated @RequestBody UtenteDTO dto, BindingResult bindingResult)
            throws BindingException, DuplicateException {
        if(bindingResult.hasErrors()) {
            String errMsg = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            logger.warn(errMsg);

            throw new BindingException(errMsg);
        }

        UtenteDTO checkUtente = utenteService.findByUsername(dto.getUsername());
        if (checkUtente != null) {
            String errMsg = String.format("Utente con username %s già presente in anagrafica", dto.getUsername());
            logger.warn(errMsg);

            throw new DuplicateException(errMsg);
        }

        utenteService.createUser(dto);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Inserimento Utente " + dto.getUsername() + " Eseguito Con Successo");

        return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
    }

    @GetMapping("/cerca/username/{username}")
    public ResponseEntity<UtenteDTO> findUserByUsername(@PathVariable String username) throws NotFoundException {
        logger.info("******* Ricerchiamo utente con username {} *******", username);

        UtenteDTO dto = utenteService.findByUsername(username);
        if(dto == null){
            String errMsg = String.format("L'utente con username %s non è stato trovato!", username);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/cerca/tutti")
    public ResponseEntity<List<Utente>> findAllUsers() throws NotFoundException {
        logger.info("******* Lista utenti *******");

        List<Utente> listaUtente = utenteService.findAll();
        if(listaUtente.isEmpty()) {
            String errMsg = "La lista utenti è vuota";
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        }

        return new ResponseEntity<>(listaUtente, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws NotFoundException {
        logger.info("******* Eliminiamo l'utente con username {}", id);

        UtenteDTO dto = utenteService.findById(id);
        if(dto == null){
            String errMsg = String.format("L'utente con username %s non è stato trovato!", id);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        }

        utenteService.deleteUserById(dto.getId());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Utente " + dto.getUsername() + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, HttpStatus.OK);
    }

}
