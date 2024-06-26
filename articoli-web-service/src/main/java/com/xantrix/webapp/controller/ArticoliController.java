package com.xantrix.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.entity.Articoli;
import com.xantrix.webapp.entity.Barcode;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.DuplicateException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.security.BearerTokenInterceptor;
import com.xantrix.webapp.service.ArticoliService;
import com.xantrix.webapp.service.BarcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("api/articoli")
@CrossOrigin( origins = "http://localhost:4200")
public class ArticoliController {

    private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);

    private final ArticoliService articoliService;

    private final BarcodeService barcodeService;

    private final ResourceBundleMessageSource errMessage;

    private final PriceClient priceClient;

    private final PromoClient promoClient;

    public ArticoliController(ArticoliService articoliService, BarcodeService barcodeService, ResourceBundleMessageSource errMessage, PriceClient priceClient, PromoClient promoClient) {
        this.articoliService = articoliService;
        this.barcodeService = barcodeService;
        this.errMessage = errMessage;
        this.priceClient = priceClient;
        this.promoClient = promoClient;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testConnex() {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Test Connessione Ok!");

        return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
    }

    private BigDecimal getPriceArt(String authHeader, String codArt, String idList) {
        BigDecimal prezzo = (!idList.isEmpty()) ? priceClient.getPriceArt(authHeader, codArt, idList) : priceClient.getDefPriceArt(authHeader, codArt);

        logger.info("Prezzo Articolo {} : {}", codArt, prezzo);
        return prezzo;
    }
    private BigDecimal getPriceArt( String codArt, String idList) {
        BigDecimal prezzo = (!idList.isEmpty()) ? priceClient.getPriceArt(codArt, idList) : priceClient.getDefPriceArt(codArt);

        logger.info("Prezzo Articolo {} : {}", codArt, prezzo);
        return prezzo;
    }

    private BigDecimal getPromoArt(String authHeader, String codArt){
        BigDecimal prezzo = promoClient.getPromoArt(authHeader, codArt);

        logger.info("Prezzo Promo Articolo {} : {}", codArt, prezzo);

        return prezzo;
    }

    // ------------------------------ RICERCA ARTICOLO PER BARCODE ------------------------------
    @GetMapping(value = "/cerca/ean/{barcode}",produces = "application/json")
    public ResponseEntity<Articoli> listArtByEan(@PathVariable String barcode, HttpServletRequest httpRequest) throws NotFoundException {
        logger.info("******** Otteniamo l'articolo con barcode {} ********", barcode);

        String authHeader = httpRequest.getHeader("Authorization");

        Articoli articolo;
        Barcode ean = barcodeService.SelByBarcode(barcode);
        if (ean == null) {
            String errMsg = String.format("Il barcode %s non è stato trovato!", barcode);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        } else {
            articolo = ean.getArticoli();
            articolo.setPrezzo(this.getPriceArt(authHeader, articolo.getCodArt(), ""));
            articolo.setPromo(this.getPromoArt(authHeader, articolo.getCodArt()));
        }
        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    // ------------------------------ RICERCA ARTICOLO PER CODICE ------------------------------
    @GetMapping("/cerca/codice/{codice}")
    public ResponseEntity<Articoli> listArtByCod(@PathVariable String codice, HttpServletRequest httpRequest) throws NotFoundException {
        logger.info("******** Otteniamo l'articolo con codice {} ********", codice);

        String authHeader = httpRequest.getHeader("Authorization");

        Articoli articolo = articoliService.selByCodArt(codice);
        if (articolo == null) {
            String errMsg = String.format("L'articolo con codice %s non è stato trovato!", codice);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        } else {
            articolo.setPrezzo(this.getPriceArt(authHeader, codice, ""));
            articolo.setPromo(this.getPromoArt(authHeader, articolo.getCodArt()));
        }

        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    // ------------------------------ RICERCA ARTICOLO PER CODICE NO AUTH ------------------------------
    @GetMapping("/noauth/cerca/codice/{codArt}")
    public ResponseEntity<Articoli> getArticolo(@PathVariable String codArt) throws NotFoundException {

        logger.info("******** Otteniamo l'articolo con codice {} ********", codArt);

        Articoli articolo = articoliService.selByCodArt(codArt);
        if (articolo == null) {
            String errMsg = String.format("L'articolo con codice %s non è stato trovato!", codArt);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        } else {
            articolo.setPrezzo(this.getPriceArt( codArt, ""));
        }

        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    // ------------------------------ RICERCA ARTICOLO PER DESCRIZIONE ------------------------------
    @GetMapping("/cerca/descrizione/{filter}")
    public ResponseEntity<List<Articoli>> listArtByDesc(@PathVariable String filter, HttpServletRequest httpRequest)  throws NotFoundException {
        logger.info("******** Otteniamo gli articoli con Descrizione : {} ********", filter);

        String authHeader = httpRequest.getHeader("Authorization");

        List<Articoli> listaArticoli = articoliService.selByDescrizione(filter.toUpperCase() + "%");
        if(listaArticoli == null){
            String errMsg = String.format("Non è stato trovato alcun articolo avente descrizione %s", filter);
            logger.warn(errMsg);

            throw new NotFoundException(errMsg);
        } else {
            listaArticoli.forEach(articolo -> articolo.setPrezzo(this.getPriceArt(authHeader, articolo.getCodArt(), "")));
            listaArticoli.forEach((articolo -> articolo.setPromo(this.getPromoArt(authHeader, articolo.getCodArt()))));
        }
        return new ResponseEntity<>(listaArticoli, HttpStatus.OK);
    }

    /**
     *
     * @param articolo
     * @param bindingResult
     * @return un articolo creato
     * @throws BindingException se l'articolo ha errori di validazione in fase di inserimento lancia l'eccezione BadRequest (400)
     * @throws DuplicateException se l'articolo e' un duplicato allora lancia l'eccezione NotAcceptable (406)
     */
    @PostMapping(value = "/inserisci", produces = "application/json")
    public ResponseEntity<?> createArt(@Validated @RequestBody Articoli articolo, BindingResult bindingResult)
            throws BindingException, DuplicateException {
        logger.info("******** Salviamo l'articolo con codice  : {} ********",articolo.getCodArt());

        //se c'e un errore di validazione parte usiamo binding
        if(bindingResult.hasErrors()){
            String errorMessage = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            logger.warn(errorMessage);

            throw new BindingException(errorMessage);
        }

        //disabilitare se si vuole gestire la modifica
        Articoli checkArt = articoliService.selByCodArt(articolo.getCodArt());
        if(checkArt != null){
            String errorMessage = String.format("Articolo %s presente in anagrafica! " +
                    "Impossibile utilizzare il metodo POST", articolo.getCodArt());

            logger.warn(errorMessage);
            throw new DuplicateException(errorMessage);
        }
        articoliService.insArticolo(articolo);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Inserimento Articolo " + articolo.getCodArt() + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
    }

    /**
     *
     * @param articolo
     * @param bindingResult
     * @return un articolo modificato
     * @throws BindingException se l'articolo ha errori di validazione in fase di inserimento lancia l'eccezione BadRequest (400)
     * @throws NotFoundException se l'articolo non esiste lancia l'eccezione NotFound (404)
     */
    @PutMapping("/modifica")
    public ResponseEntity<?> updateArt(@Validated @RequestBody Articoli articolo, BindingResult bindingResult)
            throws BindingException, NotFoundException {
        logger.info("******** Modifichiamo l'articolo con codice  : {} ********",articolo.getCodArt());

        if(bindingResult.hasErrors()){
            String errorMessage = errMessage.getMessage(Objects.requireNonNull(bindingResult.getFieldError()), LocaleContextHolder.getLocale());
            logger.warn(errorMessage);

            throw new BindingException(errorMessage);
        }

        Articoli checkArt = articoliService.selByCodArt(articolo.getCodArt());
        if(checkArt == null){
            String errorMessage = String.format("Articolo %s non presente in anagrafica! " +
                    "Impossibile utilizzare il metodo PUT", articolo.getCodArt());

            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }
        articoliService.insArticolo(articolo);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Modifica dell'articolo " + articolo.getCodArt() + " Eseguita Con Successo");
        return new ResponseEntity<>(responseNode, HttpStatus.CREATED);
    }

    /**
     *
     * @param codArt
     * @return elimina un articolo
     * @throws NotFoundException se l'articolo non esiste lancia l'eccezione NotFound (404)
     */
    @DeleteMapping("/elimina/{codArt}")
    public ResponseEntity<?> deleteArt(@PathVariable String codArt) throws NotFoundException {
        logger.info("******** Eliminiamo l'articolo con codice  : {} ********", codArt);

        Articoli articolo = articoliService.selByCodArt(codArt);
        if(articolo == null) {
            String errorMessage = String.format("Articolo %s non presente in anagrafica! ", codArt);

            logger.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }

        articoliService.delArticolo(articolo);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Articolo " + codArt + " Eseguita Con Successo");
        return new ResponseEntity<>(responseNode, HttpStatus.OK);
    }

}
