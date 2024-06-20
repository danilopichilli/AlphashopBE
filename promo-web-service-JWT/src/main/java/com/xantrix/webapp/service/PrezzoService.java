package com.xantrix.webapp.service;

import java.math.BigDecimal;

public interface PrezzoService
{
	BigDecimal selPromoByCodArt(String codArt);

	BigDecimal selPromoByCodArtAndFid(String codArt);

	BigDecimal selByCodArtAndCodFid(String codArt, String codFid);
	
	void updOggettoPromo(String oggetto, Long id);
}
