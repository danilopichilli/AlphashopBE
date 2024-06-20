package com.xantrix.webapp.controller;

 
import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 
import com.xantrix.webapp.service.PrezzoService;


@RestController
@RequestMapping(value = "api/promo/prezzo")
public class PrezzoController {

	private static final Logger logger = LoggerFactory.getLogger(PrezzoController.class);

	private final PrezzoService promoService;

    public PrezzoController(PrezzoService promoService) {
        this.promoService = promoService;
    }

    // ------------------- SELEZIONE PREZZO PROMO ------------------------------------
	@GetMapping(value = {"/{codArt}/{codFid}", "/{codArt}"})
	public BigDecimal getPricePromo(@PathVariable String codArt, @PathVariable Optional<String> codFid)
	{
		BigDecimal retVal;
		
		if (codFid.isPresent()) {
			logger.info(String.format("Cerchiamo promo riservata all fidelity %s dell'articolo %s ",codFid,codArt));
			
			retVal = promoService.selByCodArtAndCodFid(codArt, codFid.get());
		}
		else {
			logger.info(String.format("Cerchiamo Prezzo in promo articolo %s ",codArt));
			
			retVal = promoService.selPromoByCodArt(codArt);
		}
		
		return retVal;
	}
	
	// ------------------- SELEZIONE PREZZO PROMO FIDELITY ------------------------------------
	@GetMapping(value = {"/fidelity/{codArt}"})
	public BigDecimal getPricePromoFid(@PathVariable String codArt)
	{
		BigDecimal retVal = new BigDecimal("0.0");
		
		logger.info(String.format("Cerchiamo promo fidelity articolo %s ",codArt));
			
		retVal = promoService.selPromoByCodArtAndFid(codArt);
		
		return retVal;
	}
	
	
}
