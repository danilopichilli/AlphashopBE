package com.xantrix.webapp.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.xantrix.webapp.entity.DettPromo;
import com.xantrix.webapp.repository.PrezziPromoRepository;

import java.math.BigDecimal;

@Service
@Transactional
public class PrezzoServiceImpl implements PrezzoService {

	private static final Logger logger = LoggerFactory.getLogger(PrezzoService.class);


	private final PrezziPromoRepository prezziPromoRep;

    public PrezzoServiceImpl(PrezziPromoRepository prezziPromoRep) {
        this.prezziPromoRep = prezziPromoRep;
    }

    @Override
	public BigDecimal selPromoByCodArt(String codArt)
	{
		BigDecimal retVal = new BigDecimal("0");

		DettPromo promo =  prezziPromoRep.selByCodArt(codArt);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = BigDecimal.valueOf(Double.parseDouble(promo.getOggetto().replace(",", ".")));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
			else  //TODO Gestire gli altri tipi di promozione
			{
				retVal = new BigDecimal("0");
			}
		}
		else
		{
			logger.warn("Promo Articolo Assente!!");
		}

		return retVal;
	}
	
	@Override
	public BigDecimal selPromoByCodArtAndFid(String codArt)
	{
		BigDecimal retVal = new BigDecimal("0");

		DettPromo promo =  prezziPromoRep.selByCodArtAndFid(codArt);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = BigDecimal.valueOf(Double.parseDouble(promo.getOggetto().replace(",", ".")));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
			else  //TODO Gestire gli altri tipi di promozione
			{
				retVal = new BigDecimal("0");
			}
		}
		else
		{
			logger.warn("Promo Articolo Fidelity Assente!!");
		}

		return retVal;
	}
	
	@Override
	public BigDecimal selByCodArtAndCodFid(String codArt, String codFid)
	{
		BigDecimal retVal = new BigDecimal("0");

		DettPromo promo =  prezziPromoRep.selByCodArtAndCodFid(codArt, codFid);
		
		if (promo != null)
		{
			if (promo.getTipoPromo().getIdTipoPromo() == 1)
			{
				try
				{
					retVal = BigDecimal.valueOf(Double.parseDouble(promo.getOggetto().replace(",", ".")));
				}
				catch(NumberFormatException ex)
				{
					logger.warn(ex.getMessage());
				}
			}
		}
		else
		{
			logger.warn(String.format("Promo Riservata Fidelity %s Assente!!", codFid) );
		}

		return retVal;
	}
	
	@Override
	public void updOggettoPromo(String oggetto, Long id)
	{
		// TODO Auto-generated method stub
	}
	
}
