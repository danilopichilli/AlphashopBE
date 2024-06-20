package com.xantrix.webapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Articoli  implements Serializable {

	private static final long serialVersionUID = 291353626011036772L;

	private String codArt;
	private String descrizione;	
	private String um;
	private String codStat;
	private Integer pzCart;
	private Double pesoNetto;
	private String idStatoArt;
	private Date dataCreaz;
	private BigDecimal prezzo;
	
}
