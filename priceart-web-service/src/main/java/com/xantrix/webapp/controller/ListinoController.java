package com.xantrix.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.entity.Listini;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.service.ListinoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/listino")
public class ListinoController {

    private static final Logger logger = LoggerFactory.getLogger(ListinoController.class);

    private final ListinoService listinoService;

    private final ResourceBundleMessageSource errMessage;

    public ListinoController(ListinoService listinoService, ResourceBundleMessageSource errMessage) {
        this.listinoService = listinoService;
        this.errMessage = errMessage;
    }

    @PostMapping("/inserisci")
    public ResponseEntity<?> inserisciListino(@Validated @RequestBody Listini listini, BindingResult bindingResult) throws BindingException, DuplicateException {

        logger.info("******** Salviamo l'articolo con codice  : {} ********",listini.getId());

        if(bindingResult.hasErrors()) {
            String errorMessage = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            logger.warn(errorMessage);

            throw new BindingException(errorMessage);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        listinoService.saveListino(listini);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Inserimento Listino " + listini.getId() + " Eseguito Con Successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
    }

    @GetMapping("/cerca/id/{id}")
    public ResponseEntity<Optional<Listini>> getListinoById(@PathVariable String id) throws NotFoundException {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        logger.info("******** Cerchiamo listino con numero  : {} ********",id);

        Optional<Listini> listini = listinoService.findListinoById(id);
        if(listini.isEmpty()){
            String errMsg = String.format("Listino %s non presente in anagrafica", id);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        }

        return new ResponseEntity<>(listini, HttpStatus.OK);
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> deleteListinoById(@PathVariable String id) throws NotFoundException {
        logger.info("******** Eliminiamo listino con codice  : {} ********",id);

        Optional<Listini> listini = listinoService.findListinoById(id);
        if(listini.isEmpty()){
            String errMsg = String.format("Listino %s non presente in anagrafica!", id);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        }

        listinoService.deleteListino(listini.get());
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Listino " + id + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
    }
}
