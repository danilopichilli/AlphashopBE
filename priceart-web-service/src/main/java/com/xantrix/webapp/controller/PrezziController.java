package com.xantrix.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.config.AppConfig;
import com.xantrix.webapp.entity.DettListini;
import com.xantrix.webapp.service.PrezziService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/prezzi")
public class PrezziController {

    private static final Logger logger = LoggerFactory.getLogger(ListinoController.class);

    private final PrezziService prezziService;

    private final AppConfig appConfig;

    private final ResourceBundleMessageSource errMessage;

    public PrezziController(PrezziService prezziService, AppConfig appConfig, ResourceBundleMessageSource errMessage) {
        this.prezziService = prezziService;
        this.appConfig = appConfig;
        this.errMessage = errMessage;
    }

    @GetMapping({"/{codArt}/{idList}","/{codArt}"})
    public BigDecimal getPriceCodArt(@PathVariable String codArt, @PathVariable Optional<String> idList) {
        BigDecimal retVal = new BigDecimal(0);

        String id = idList.orElseGet(appConfig::getListino);

        logger.info("Listino di Riferimento: {}", idList);
        DettListini prezzo = prezziService.findByCodArtAndIdList(codArt, id);
        if (prezzo != null) {
            logger.info("Prezzo Articolo: {}", prezzo.getPrezzo());
            retVal = prezzo.getPrezzo();
        } else {
            logger.error("Prezzo Articolo Assente!!");
        }
        return retVal;
    }

    @GetMapping({"/noauth/{codArt}/{idList}","/noauth/{codArt}"})
    public BigDecimal getPriceCodArtNoAuth(@PathVariable String codArt, @PathVariable Optional<String> idList) {
        BigDecimal retVal = new BigDecimal(0);

        String id = idList.orElseGet(appConfig::getListino);

        logger.info("Listino di Riferimento: {}", idList);
        DettListini prezzo = prezziService.findByCodArtAndIdList(codArt, id);
        if (prezzo != null) {
            logger.info("Prezzo Articolo: {}", prezzo.getPrezzo());
            retVal = prezzo.getPrezzo();
        } else {
            logger.error("Prezzo Articolo Assente!!");
        }
        return retVal;
    }

    @DeleteMapping("/elimina/{codArt}/{idList}")
    public ResponseEntity<?> deletePrice(@PathVariable String codArt, @PathVariable String idList) {

        logger.info(String.format("Eliminazione presso listino %s dell'articolo %s", idList, codArt));

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        prezziService.deleteByCodArtAndIdList(codArt, idList);

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Prezzo Eseguita Con Successo");

        logger.info("Eliminazione Prezzo Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
